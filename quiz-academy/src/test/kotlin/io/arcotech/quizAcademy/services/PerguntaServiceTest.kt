package io.arcotech.quizAcademy.services

import io.arcotech.quizAcademy.dto.CategoriaPerguntaView
import io.arcotech.quizAcademy.mappers.NovaPerguntaFormMapper
import io.arcotech.quizAcademy.mappers.PerguntaViewMapper
import io.arcotech.quizAcademy.models.NivelPergunta
import io.arcotech.quizAcademy.models.Pergunta
import io.arcotech.quizAcademy.repositories.PerguntaRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.mockito.Mockito
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

class PerguntaServiceTest {

    private lateinit var perguntaRepository: PerguntaRepository

    private lateinit var novaPerguntaFormMapper: NovaPerguntaFormMapper

    private lateinit var perguntaViewMapper: PerguntaViewMapper

    private lateinit var perguntaService: PerguntaService

    private val listaPerguntas = listOf(
        Pergunta(
            1L,
            "Quem descobriu o Brasil?",
            NivelPergunta.BASICO,
            "História",
            "Eduardo"),
        Pergunta(
            2L,
            "Quanto é 2 x 2?",
            NivelPergunta.BASICO,
            "Matemática",
            "Edileuza"),
    )

    @BeforeEach
    fun setUp() {
        perguntaRepository = Mockito.mock(PerguntaRepository::class.java)
        perguntaViewMapper= PerguntaViewMapper()
        novaPerguntaFormMapper= NovaPerguntaFormMapper()
        perguntaService = PerguntaService(perguntaRepository, perguntaViewMapper, novaPerguntaFormMapper)
    }

    @ParameterizedTest
    @CsvSource(
        "null",
        "Eduardo"
    )
    fun `listar deve retornar lista de perguntas paginadas`(
        autor: String
    ){
        // given
        val paginacao =  Mockito.mock(Pageable::class.java)
        var nomeAutor : String? = null;
        if (autor == "null") {
            Mockito.`when`(perguntaRepository.findAll(paginacao)).thenReturn(PageImpl(listaPerguntas))

        } else {
            nomeAutor = autor
            Mockito.`when`(perguntaRepository.findByAutor(nomeAutor, paginacao)).thenReturn(PageImpl(listaPerguntas))
        }
        // when
        val resultados = perguntaService.listar(nomeAutor, paginacao)

        // then
        assertEquals(2, resultados.content.size)
        assertEquals("Quem descobriu o Brasil?", resultados.content[0].pergunta)
        assertEquals("Quanto é 2 x 2?", resultados.content[1].pergunta)
    }

    @Test
    fun `relatorioCategoria deve retornar categorias`(){
        // given
        val categorias = listOf(
            CategoriaPerguntaView("Matemática ", 10),
            CategoriaPerguntaView("História", 5)
        )
        Mockito.`when`(perguntaRepository.relatorioCategorias()).thenReturn(categorias)

        // when
        val resultado = perguntaService.relatorioCategoria()

        // then
        assertEquals(categorias, resultado)
        Mockito.verify(perguntaRepository, Mockito.times(1)).relatorioCategorias()

    }


}