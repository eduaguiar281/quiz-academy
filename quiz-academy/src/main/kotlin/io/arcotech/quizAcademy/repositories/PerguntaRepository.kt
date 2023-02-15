package io.arcotech.quizAcademy.repositories

import io.arcotech.quizAcademy.dto.CategoriaPerguntaView
import io.arcotech.quizAcademy.models.Pergunta
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PerguntaRepository : JpaRepository<Pergunta, Long> {
    fun findByAutor(autor: String, paginacao: Pageable): Page<Pergunta>

    @Query("SELECT new io.arcotech.quizAcademy.dto.CategoriaPerguntaView(p.categoria, count(p)) FROM Pergunta p GROUP BY p.categoria")
    fun relatorioCategorias(): List<CategoriaPerguntaView>
}