package io.arcotech.quizAcademy.models

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class Pergunta (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Long? = null,
    var descricao: String,
    @Enumerated(value = EnumType.STRING)
    var nivel: NivelPergunta,
    var categoria: String? = null,
    var autor: String? = null,
    @OneToMany(mappedBy = "pergunta")
    val respostas: List<Resposta> = ArrayList(),
    override val dataHoraCadastro: LocalDateTime = LocalDateTime.now()
):Entidade(id, dataHoraCadastro)