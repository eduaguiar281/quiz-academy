package io.arcotech.quizAcademy.controllers

import io.arcotech.quizAcademy.dto.AlteraPerguntaForm
import io.arcotech.quizAcademy.dto.NovaPerguntaForm
import io.arcotech.quizAcademy.models.Pergunta
import io.arcotech.quizAcademy.services.PerguntaService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/perguntas")
class PerguntasController (val perguntaService: PerguntaService) {

    @GetMapping
    fun listar(): List<Pergunta>{
        return perguntaService.listar()
    }
    @GetMapping("/{id}")
    fun ObterPorId(@PathVariable id: Long): Pergunta{
        return perguntaService.obterPorId(id);
    }

    @PostMapping
    fun cadastrar(@RequestBody form: NovaPerguntaForm){
        perguntaService.cadastrar(form)
    }

    @PutMapping
    fun alterar(@RequestBody form: AlteraPerguntaForm){
        perguntaService.alterar(form)
    }

    @DeleteMapping("/{id}")
    fun excluir(@PathVariable id: Long){
        perguntaService.deletar(id)
    }

}