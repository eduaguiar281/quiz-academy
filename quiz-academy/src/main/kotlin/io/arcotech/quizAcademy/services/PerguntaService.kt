package io.arcotech.quizAcademy.services

import io.arcotech.quizAcademy.models.NivelPergunta
import io.arcotech.quizAcademy.models.Pergunta
import kotlin.streams.toList

class PerguntaService (
    var perguntasList: MutableList<Pergunta> = mutableListOf()
){

    init{
        perguntasList.addAll(listOf(
            Pergunta(1, "Quem descobriu o Brasil?", NivelPergunta.BASICO),
            Pergunta(2, "Qual é a cor do cavalo branco de Napoleão?", NivelPergunta.BASICO),
            Pergunta(3, "Em que ano foi declarado a independencia dos EUA?", NivelPergunta.AVANCADO)
            ))
    }

    fun listar(): List<Pergunta> {
        return perguntasList
            .stream()
            .toList()
    }

    fun obterPorId(id: Long): Pergunta{
        return perguntasList.stream()
            .filter({p -> p.id == id})
            .findFirst().get()
    }
}
