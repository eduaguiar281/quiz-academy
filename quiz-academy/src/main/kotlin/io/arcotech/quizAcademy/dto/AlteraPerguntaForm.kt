package io.arcotech.quizAcademy.dto

import io.arcotech.quizAcademy.models.NivelPergunta
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

class AlteraPerguntaForm (
    @field:NotNull(message = "Campo Id é obrigatório!")
    val id: Long,
    @field:NotEmpty(message = "Pergunta é obrigatória!")
    @field:Size(min = 10, max = 250, message = "Pergunta deve ter entre 10 e 250 caracteres!")
    val pergunta: String,
    @field:NotNull(message = "Nível da pergunta é obrigatório!")
    val nivelPergunta: NivelPergunta
)