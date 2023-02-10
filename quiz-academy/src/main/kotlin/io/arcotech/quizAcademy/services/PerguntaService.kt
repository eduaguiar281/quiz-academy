package io.arcotech.quizAcademy.services

import io.arcotech.quizAcademy.dto.AlteraPerguntaForm
import io.arcotech.quizAcademy.dto.NovaPerguntaForm
import io.arcotech.quizAcademy.exceptions.NotFoundException
import io.arcotech.quizAcademy.models.NivelPergunta
import io.arcotech.quizAcademy.models.Pergunta
import org.springframework.stereotype.Service

@Service
class PerguntaService (
    var perguntasList: MutableList<Pergunta> = mutableListOf(),
    val notFoundMessage: String = "Pergunta não foi localizada!"
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
            .findFirst().orElseThrow{(NotFoundException(notFoundMessage))}
    }

    fun cadastrar(form: NovaPerguntaForm): Pergunta{
        val novoId = perguntasList.size.toLong() + 1
        val novaPergunta = Pergunta(novoId, form.pergunta, form.nivelPergunta)
        perguntasList.add(novaPergunta)
        return novaPergunta
    }

    fun alterar(form: AlteraPerguntaForm): Pergunta{
        val pergunta = perguntasList
            .stream()
            .filter({p -> p.id == form.id})
            .findFirst().orElseThrow{(NotFoundException(notFoundMessage))}
        pergunta.descricao = form.pergunta
        pergunta.nivel = form.nivelPergunta
        return pergunta
    }

    fun deletar(id: Long){
        val pergunta = perguntasList
            .stream()
            .filter({p -> p.id == id})
            .findFirst().orElseThrow{(NotFoundException(notFoundMessage))}
        perguntasList.remove(pergunta)
    }
}
