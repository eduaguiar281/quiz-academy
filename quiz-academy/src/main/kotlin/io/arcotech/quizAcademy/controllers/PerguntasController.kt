package io.arcotech.quizAcademy.controllers

import io.arcotech.quizAcademy.dto.AlteraPerguntaForm
import io.arcotech.quizAcademy.dto.NovaPerguntaForm
import io.arcotech.quizAcademy.models.Pergunta
import io.arcotech.quizAcademy.services.PerguntaService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

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
    fun cadastrar(
        uriBuilder: UriComponentsBuilder,
        @RequestBody form: NovaPerguntaForm
    ): ResponseEntity<Pergunta> {
        val novaPergunta = perguntaService.cadastrar(form)
        val uri = uriBuilder.path("/perguntas/${novaPergunta.id}").build().toUri()
        return ResponseEntity.created(uri).body(novaPergunta)
    }

    @PutMapping
    fun alterar(
        @RequestBody form: AlteraPerguntaForm
    ):ResponseEntity<Pergunta> {
        return ResponseEntity.ok(perguntaService.alterar(form))
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun excluir(@PathVariable id: Long){
        perguntaService.deletar(id)
    }

}