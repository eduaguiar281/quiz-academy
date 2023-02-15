package io.arcotech.quizAcademy.mappers

import io.arcotech.quizAcademy.dto.PerguntaView
import io.arcotech.quizAcademy.models.Pergunta
import org.springframework.stereotype.Component

@Component
class PerguntaViewMapper: Mapper<Pergunta, PerguntaView>  {
    override fun map(t: Pergunta): PerguntaView {
        return PerguntaView(
            t.id,
            t.descricao,
            t.nivel,
            t.categoria,
            t.autor,
            t.dataHoraCadastro
        )
    }
}