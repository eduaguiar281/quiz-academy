package io.arcotech.quizAcademy.models

import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.ManyToOne
import java.time.LocalDateTime
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
data class Resposta (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long?,
    var resposta: String,
    var fonte: String,
    @ManyToOne
    val pergunta: Pergunta,
    override val dataHoraCadastro: LocalDateTime = LocalDateTime.now()
):Entidade(id, dataHoraCadastro)
