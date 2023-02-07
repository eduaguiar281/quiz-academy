package io.arcotech.quizAcademy.models

import java.time.LocalDateTime

class Resposta (
    override val id: Long?,
    var resposta: String,
    var fonte: String,
    override val dataHoraCadastro: LocalDateTime = LocalDateTime.now()
):Entidade() {
}
