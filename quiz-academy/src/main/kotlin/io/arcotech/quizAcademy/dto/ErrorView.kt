package io.arcotech.quizAcademy.dto

import java.time.LocalDateTime

class ErrorView (
    val timeStamp: LocalDateTime = LocalDateTime.now(),
    val status: Int,
    val error: String,
    val message: String?,
    val path: String
)