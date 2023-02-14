package io.arcotech.quizAcademy.repositories

import io.arcotech.quizAcademy.models.Pergunta
import org.springframework.data.jpa.repository.JpaRepository

interface PerguntaRepository : JpaRepository<Pergunta, Long> {
}