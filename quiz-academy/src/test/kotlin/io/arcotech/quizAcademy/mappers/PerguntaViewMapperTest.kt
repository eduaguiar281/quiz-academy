package io.arcotech.quizAcademy.mappers

import io.arcotech.quizAcademy.models.NivelPergunta
import io.arcotech.quizAcademy.models.Pergunta
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PerguntaViewMapperTest {

    @Test
    fun `map deve retornar uma PerguntaView`(){
        // given
        val pergunta = Pergunta(
            1L,
            "Quem descobriu o Brasil?",
            NivelPergunta.BASICO,
            "História",
            "Eduardo"
        )
        val mapper = PerguntaViewMapper()

        // when
        val perguntaView = mapper.map(pergunta)

        // then
        assertEquals(pergunta.id, perguntaView.id)
        assertEquals(pergunta.descricao, perguntaView.pergunta)
        assertEquals(pergunta.nivel, perguntaView.nivelPergunta)
        assertEquals(pergunta.categoria, perguntaView.categoria)
        assertEquals(pergunta.autor, perguntaView.autor)
        assertEquals(pergunta.dataHoraCadastro, perguntaView.dataHoraCadastro)
    }
}