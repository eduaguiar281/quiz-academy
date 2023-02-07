package io.arcotech.quizAcademy.dto

import io.arcotech.quizAcademy.models.NivelPergunta

data class NovaPerguntaForm (
    val pergunta: String,
    val nivelPergunta: NivelPergunta
)