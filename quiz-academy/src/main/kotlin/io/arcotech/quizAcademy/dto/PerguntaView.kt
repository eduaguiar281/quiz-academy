package io.arcotech.quizAcademy.dto

import io.arcotech.quizAcademy.models.NivelPergunta
import java.time.LocalDateTime

data class PerguntaView (
       val id: Long?,
       val pergunta: String,
       val nivelPergunta: NivelPergunta,
       val categoria: String? = null,
       val autor: String? = null,
       val dataHoraCadastro: LocalDateTime = LocalDateTime.now()
){
       constructor() : this (null, "", NivelPergunta.BASICO)
}