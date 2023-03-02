package io.arcotech.quizAcademy.mappers

import io.arcotech.quizAcademy.dto.AlteraPerguntaForm
import io.arcotech.quizAcademy.models.NivelPergunta
import io.arcotech.quizAcademy.models.Pergunta
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AlteraPerguntaFormMapperTest {

    @Test
    fun `mapFrom deve mapear instancia de Pergunta`(){
        // given
        val pergunta = Pergunta(
            1L,
            "Quanto é 2 x 2?",
            NivelPergunta.BASICO,
            "Matemática",
            "Eduardo"
        )
        val alteraPergunta = AlteraPerguntaForm(
            2L,
            "Quanto é 2 + 2?",
            NivelPergunta.BASICO,
            "Matemática",
            "Edileuza"
        )

        // when
        pergunta.mapFrom(alteraPergunta)

        // then
        assertEquals(pergunta.descricao, alteraPergunta.pergunta)
        assertEquals(pergunta.nivel, alteraPergunta.nivelPergunta)
        assertEquals(pergunta.categoria, alteraPergunta.categoria)
        assertEquals(pergunta.autor, alteraPergunta.autor)
        Assertions.assertNotEquals(pergunta.id, alteraPergunta.id)
    }
}