package io.arcotech.quizAcademy.dto

import io.arcotech.quizAcademy.models.NivelPergunta

class AlteraPerguntaForm (
    val id: Long,
    val pergunta: String,
    val nivelPergunta: NivelPergunta
)