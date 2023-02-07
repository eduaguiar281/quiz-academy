package io.arcotech.quizAcademy.services

import io.arcotech.quizAcademy.dto.AlteraPerguntaForm
import io.arcotech.quizAcademy.dto.NovaPerguntaForm
import io.arcotech.quizAcademy.models.NivelPergunta
import io.arcotech.quizAcademy.models.Pergunta
import org.springframework.stereotype.Service

@Service
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

    fun cadastrar(form: NovaPerguntaForm){
        val novoId = perguntasList.size.toLong() + 1
        perguntasList.add(Pergunta(novoId, form.pergunta, form.nivelPergunta))
    }

    fun alterar(form: AlteraPerguntaForm){
        val pergunta = perguntasList
            .stream()
            .filter({p -> p.id == form.id})
            .findFirst().get()
        pergunta.descricao = form.pergunta
        pergunta.nivel = form.nivelPergunta
    }

    fun deletar(id: Long){
        val pergunta = perguntasList
            .stream()
            .filter({p -> p.id == id})
            .findFirst().get()
        perguntasList.remove(pergunta)
    }
}
