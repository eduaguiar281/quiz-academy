package io.arcotech.quizAcademy.integration.controllers

import RabbitMQListener
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.jayway.jsonpath.JsonPath
import com.rabbitmq.client.*
import io.arcotech.quizAcademy.dto.NovaPerguntaForm
import io.arcotech.quizAcademy.dto.PerguntaView
import io.arcotech.quizAcademy.models.NivelPergunta
import io.arcotech.quizAcademy.models.Pergunta
import io.arcotech.quizAcademy.serializers.CustomLocalDateTimeSerializer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.containers.RabbitMQContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.CompletableFuture

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class PerguntasControllerTests {

    @Autowired
    private lateinit var mockMvc: MockMvc

    companion object {
        @Container
        val postgresContainer: PostgreSQLContainer<*> = PostgreSQLContainer(DockerImageName.parse("postgres:15.3"))
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpassword")

        @Container
        val rabbitMqContainer = RabbitMQContainer(DockerImageName.parse("rabbitmq:3.7.25-management-alpine"))

        init {
            postgresContainer.start()
            rabbitMqContainer.start()

            // Define a propriedade spring.datasource.url com a URL do banco de dados do contêiner
            System.setProperty("spring.datasource.url", postgresContainer.jdbcUrl)
            System.setProperty("spring.datasource.username", postgresContainer.username)
            System.setProperty("spring.datasource.password", postgresContainer.password)
            System.setProperty("spring.rabbitmq.host", rabbitMqContainer.host)
            System.setProperty("spring.rabbitmq.port", rabbitMqContainer.amqpPort.toString())
            System.setProperty("spring.rabbitmq.username", rabbitMqContainer.adminUsername)
            System.setProperty("spring.rabbitmq.password", rabbitMqContainer.adminPassword)
        }
    }
    @Test
    fun `Cadastro de Pergunta Quando Sucesso deve Cadastrar no Banco e Enviar Para Fila`(){
        // Given
        val request = NovaPerguntaForm("Some 2 + 2",
            NivelPergunta.BASICO,
            "Matematica",
            "Professor Raimundo")

        var objectMapper = ObjectMapper().registerModule(JavaTimeModule())
        objectMapper.registerModule(SimpleModule()
            .addSerializer(LocalDateTime::class.java, CustomLocalDateTimeSerializer("yyyy-MM-dd'T'HH:mm:ss.SSS")))
        val requestBody = objectMapper.writeValueAsString(request)

        // When & Then
        val createdResult = mockMvc.perform(
            MockMvcRequestBuilders.post("/perguntas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        )
        .andExpect(MockMvcResultMatchers.status().isCreated)
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
        .andReturn()

        val perguntaId: Long = JsonPath.read(createdResult.response.contentAsString, "$.id")

        val getResult = mockMvc.perform(
            MockMvcRequestBuilders.get("/perguntas/$perguntaId")
        )
        .andExpect(MockMvcResultMatchers.status().isOk)
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
        .andReturn()

        val perguntaView: PerguntaView =  objectMapper.readValue(getResult.response.contentAsString, PerguntaView::class.java)

        assertEquals(request.pergunta, perguntaView.pergunta)
        assertEquals(perguntaId, perguntaView.id)
        assertEquals(request.nivelPergunta, perguntaView.nivelPergunta)
        assertEquals(request.categoria, perguntaView.categoria)
        assertEquals(request.autor, perguntaView.autor)

        val message = read("quiz-pergunta-cadastrada")
        objectMapper = ObjectMapper().registerModule(JavaTimeModule())
        objectMapper.registerModule(
            SimpleModule()
                .addDeserializer(LocalDateTime::class.java, LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))))


        val pergunta: Pergunta = objectMapper.readValue(message, Pergunta::class.java)
        assertEquals(pergunta.descricao, perguntaView.pergunta)
        assertEquals(pergunta.id, perguntaView.id)
        assertEquals(pergunta.nivel, perguntaView.nivelPergunta)
        assertEquals(pergunta.categoria, perguntaView.categoria)
        assertEquals(pergunta.autor, perguntaView.autor)
    }

    private fun read(queue: String): String?{
        val factory = ConnectionFactory()
        factory.host = rabbitMqContainer.host
        factory.port = rabbitMqContainer.amqpPort
        factory.username = rabbitMqContainer.adminUsername
        factory.password = rabbitMqContainer.adminPassword
        val connection = factory.newConnection()
        val channel = connection.createChannel()

        // Começa a consumir mensagens da fila
        val response = channel.basicGet(queue, true)
        if (response != null) {
            val message = String(response.body, charset("UTF-8"))
            return message
        }
        return null

    }
}