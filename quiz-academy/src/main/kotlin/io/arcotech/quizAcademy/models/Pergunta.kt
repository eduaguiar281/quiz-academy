package io.arcotech.quizAcademy.models

import java.time.LocalDateTime

class Pergunta (
    id: Long?,
    var descricao: String,
    val nivel: NivelPergunta,
    val respostas: List<Resposta> = ArrayList(),
    dataHoraCadastro: LocalDateTime = LocalDateTime.now()
):Entidade(id, dataHoraCadastro)  {
}