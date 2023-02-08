package io.arcotech.quizAcademy.models

import java.time.LocalDateTime

abstract class Entidade (
    val id: Long? = null,
    val dataHoraCadastro: LocalDateTime
    )