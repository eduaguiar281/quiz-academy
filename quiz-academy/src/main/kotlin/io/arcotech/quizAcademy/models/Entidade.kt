package io.arcotech.quizAcademy.models

import java.time.LocalDateTime

abstract class Entidade {
    open abstract val id: Long?
    open abstract val dataHoraCadastro: LocalDateTime
}