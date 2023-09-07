package io.arcotech.quizAcademy.services

import io.arcotech.quizAcademy.dto.AlteraPerguntaForm
import io.arcotech.quizAcademy.dto.CategoriaPerguntaView
import io.arcotech.quizAcademy.dto.NovaPerguntaForm
import io.arcotech.quizAcademy.dto.PerguntaView
import io.arcotech.quizAcademy.exceptions.NotFoundException
import io.arcotech.quizAcademy.mappers.NovaPerguntaFormMapper
import io.arcotech.quizAcademy.mappers.PerguntaViewMapper
import io.arcotech.quizAcademy.mappers.mapFrom
import io.arcotech.quizAcademy.messages.RabbitMQProducer
import io.arcotech.quizAcademy.repositories.PerguntaRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class PerguntaService (
    private val perguntaRepository: PerguntaRepository,
    private val perguntaViewMapper: PerguntaViewMapper,
    private val novaPerguntaFormMapper: NovaPerguntaFormMapper,
    private val rabbitMQProducer: RabbitMQProducer,
    private val notFoundMessage: String = "Pergunta não foi localizada!"
){

    fun listar(autor: String?, paginacao: Pageable): Page<PerguntaView> {

        val results = if (autor == null){
            perguntaRepository.findAll(paginacao)
        }
        else{
            perguntaRepository.findByAutor(autor, paginacao)
        }
        return results.map { p -> perguntaViewMapper.map(p) }
    }

    fun obterPorId(id: Long): PerguntaView{
        val pergunta =  perguntaRepository.findById(id)
            .orElseThrow{(NotFoundException(notFoundMessage))}
        return perguntaViewMapper.map(pergunta)
    }

    fun cadastrar(form: NovaPerguntaForm): PerguntaView{
        val novaPergunta = novaPerguntaFormMapper.map(form)
        perguntaRepository.save(novaPergunta)
        rabbitMQProducer.sendMessage("quiz-pergunta-cadastrada", novaPergunta)
        return perguntaViewMapper.map(novaPergunta)
    }

    fun alterar(form: AlteraPerguntaForm): PerguntaView{
        val pergunta = perguntaRepository.findById(form.id)
            .orElseThrow{(NotFoundException(notFoundMessage))}
        pergunta.mapFrom(form)
        perguntaRepository.save(pergunta)
        rabbitMQProducer.sendMessage("quiz-pergunta-alterada", pergunta)
        return perguntaViewMapper.map(pergunta)
    }

    fun deletar(id: Long){
        val pergunta = perguntaRepository.findById(id)
            .orElseThrow{(NotFoundException(notFoundMessage))}
        rabbitMQProducer.sendMessage("quiz-pergunta-excluida", pergunta)
        perguntaRepository.delete(pergunta)
    }

    fun relatorioCategoria(): List<CategoriaPerguntaView> {
        return perguntaRepository.relatorioCategorias()
    }
}
