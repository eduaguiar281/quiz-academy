package io.arcotech.quizAcademy.messages

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.arcotech.quizAcademy.serializers.CustomLocalDateTimeSerializer
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class RabbitMQProducer @Autowired constructor(private val rabbitTemplate: RabbitTemplate) {
    fun <T> sendMessage(queueName: String, objectMeesage: T) {

        val objectMapper = ObjectMapper().registerModule(JavaTimeModule())
        objectMapper.registerModule(
            SimpleModule()
                .addSerializer(LocalDateTime::class.java, CustomLocalDateTimeSerializer()))

        val message = objectMapper.writeValueAsString(objectMeesage)
        rabbitTemplate.convertAndSend(queueName, message)
        println("Mensagem enviada: $message")
    }
}