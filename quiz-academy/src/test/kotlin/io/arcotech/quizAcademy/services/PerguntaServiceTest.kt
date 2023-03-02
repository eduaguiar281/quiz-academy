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
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable

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

    @Test
    fun `listar sem autor deve retornar lista de perguntas paginadas`(
    ){
        // given
        val paginacao =  Mockito.mock(Pageable::class.java)
        Mockito.`when`(perguntaRepository.findAll(paginacao)).thenReturn(PageImpl(listaPerguntas))

        // when
        val resultados = perguntaService.listar(null, paginacao)

        // then
        assertEquals(2, resultados.content.size)
        assertEquals(perguntaViewMapper.map(listaPerguntas[0]), resultados.content[0])
        assertEquals(perguntaViewMapper.map(listaPerguntas[1]), resultados.content[1])
        Mockito.verify(perguntaRepository, Mockito.times(1)).findAll(paginacao)
    }

    @Test
    fun `listar com autor deve retornar lista de perguntas paginadas`(
    ){
        // given
        val paginacao =  Mockito.mock(Pageable::class.java)
        Mockito.`when`(perguntaRepository.findByAutor("Eduardo", paginacao)).thenReturn(PageImpl(listaPerguntas))

        // when
        val resultados = perguntaService.listar("Eduardo", paginacao)

        // then
        assertEquals(2, resultados.content.size)
        assertEquals(perguntaViewMapper.map(listaPerguntas[0]), resultados.content[0])
        assertEquals(perguntaViewMapper.map(listaPerguntas[1]), resultados.content[1])
        Mockito.verify(perguntaRepository, Mockito.times(1)).findByAutor("Eduardo", paginacao)
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