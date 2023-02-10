package io.arcotech.quizAcademy.models

import java.time.LocalDateTime

data class Resposta (
    override val id: Long?,
    var resposta: String,
    var fonte: String,
    override val dataHoraCadastro: LocalDateTime = LocalDateTime.now()
):Entidade(id, dataHoraCadastro)
