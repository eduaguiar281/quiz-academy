package io.arcotech.quizAcademy.models

import java.time.LocalDateTime

class Resposta (
    id: Long?,
    var resposta: String,
    var fonte: String,
    dataHoraCadastro: LocalDateTime = LocalDateTime.now()
):Entidade(id, dataHoraCadastro) {
}
