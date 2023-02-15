package io.arcotech.quizAcademy.mappers

import io.arcotech.quizAcademy.dto.AlteraPerguntaForm
import io.arcotech.quizAcademy.models.Pergunta


fun Pergunta.mapFrom(t: AlteraPerguntaForm) {
    this.descricao = t.pergunta
    this.nivel = t.nivelPergunta
    this.autor = t.autor
    this.categoria = t.categoria
}