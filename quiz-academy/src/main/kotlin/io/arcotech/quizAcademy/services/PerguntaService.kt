package io.arcotech.quizAcademy.services

import io.arcotech.quizAcademy.dto.AlteraPerguntaForm
import io.arcotech.quizAcademy.dto.NovaPerguntaForm
import io.arcotech.quizAcademy.exceptions.NotFoundException
import io.arcotech.quizAcademy.models.Pergunta
import io.arcotech.quizAcademy.repositories.PerguntaRepository
import org.springframework.stereotype.Service

@Service
class PerguntaService (
    private val perguntaRepository: PerguntaRepository,
    private val notFoundMessage: String = "Pergunta não foi localizada!"
){

    fun listar(): List<Pergunta> {
        return perguntaRepository.findAll()
    }

    fun obterPorId(id: Long): Pergunta{
        return perguntaRepository.findById(id)
            .orElseThrow{(NotFoundException(notFoundMessage))}
    }

    fun cadastrar(form: NovaPerguntaForm): Pergunta{
        val novaPergunta = Pergunta(descricao = form.pergunta, nivel = form.nivelPergunta)
        perguntaRepository.save(novaPergunta)
        return novaPergunta
    }

    fun alterar(form: AlteraPerguntaForm): Pergunta{
        val pergunta = perguntaRepository.findById(form.id)
            .orElseThrow{(NotFoundException(notFoundMessage))}
        pergunta.descricao = form.pergunta
        pergunta.nivel = form.nivelPergunta
        perguntaRepository.save(pergunta)
        return pergunta
    }

    fun deletar(id: Long){
        val pergunta = perguntaRepository.findById(id)
            .orElseThrow{(NotFoundException(notFoundMessage))}
        perguntaRepository.delete(pergunta)
    }
}
