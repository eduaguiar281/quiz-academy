package io.arcotech.quizAcademy.mappers

import io.arcotech.quizAcademy.dto.NovaPerguntaForm
import io.arcotech.quizAcademy.models.Pergunta
import org.springframework.stereotype.Component

@Component
class NovaPerguntaFormMapper : Mapper<NovaPerguntaForm, Pergunta> {
    override fun map(t: NovaPerguntaForm): Pergunta {
        return Pergunta(
            descricao = t.pergunta,
            nivel = t.nivelPergunta,
            autor =  t.autor,
            categoria = t.categoria)
    }
}