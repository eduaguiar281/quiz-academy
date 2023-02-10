package io.arcotech.quizAcademy.models

import java.time.LocalDateTime

abstract class Entidade (
    open val id: Long? = null,
    open val dataHoraCadastro: LocalDateTime = LocalDateTime.now()
)