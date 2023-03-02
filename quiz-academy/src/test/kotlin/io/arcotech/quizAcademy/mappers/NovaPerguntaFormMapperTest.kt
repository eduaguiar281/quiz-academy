package io.arcotech.quizAcademy.mappers

import io.arcotech.quizAcademy.dto.NovaPerguntaForm
import io.arcotech.quizAcademy.models.NivelPergunta
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals


class NovaPerguntaFormMapperTest {

    @Test
    fun `map deve retornar Pergunta`(){
        // given
        val novaPergunta = NovaPerguntaForm(
            "Quanto é 2 + 2?",
            NivelPergunta.BASICO,
            "Matemática",
            "Eduardo"
        )
        val mapper = NovaPerguntaFormMapper()

        // when
        val pergunta = mapper.map(novaPergunta)

        // then
        assertEquals(pergunta.id, null)
        assertEquals(pergunta.descricao, novaPergunta.pergunta)
        assertEquals(pergunta.autor, novaPergunta.autor)
        assertEquals(pergunta.categoria, novaPergunta.categoria)
    }
}