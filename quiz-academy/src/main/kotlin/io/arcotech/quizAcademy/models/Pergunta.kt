package io.arcotech.quizAcademy.models

import java.time.LocalDateTime

data class Pergunta (
    override val id: Long?,
    var descricao: String,
    var nivel: NivelPergunta,
    val respostas: List<Resposta> = ArrayList(),
    override val dataHoraCadastro: LocalDateTime = LocalDateTime.now()
):Entidade(id, dataHoraCadastro)