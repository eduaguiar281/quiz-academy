package io.arcotech.quizAcademy.controllers

import io.arcotech.quizAcademy.dto.AlteraPerguntaForm
import io.arcotech.quizAcademy.dto.CategoriaPerguntaView
import io.arcotech.quizAcademy.dto.NovaPerguntaForm
import io.arcotech.quizAcademy.dto.PerguntaView
import io.arcotech.quizAcademy.services.PerguntaService
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/perguntas")
class PerguntasController (val perguntaService: PerguntaService) {

    @GetMapping
    fun listar(@RequestParam(required = false) autor: String?): List<PerguntaView>{
        return perguntaService.listar(autor)
    }
    @GetMapping("/{id}")
    fun ObterPorId(@PathVariable id: Long): PerguntaView{
        return perguntaService.obterPorId(id);
    }

    @GetMapping("/relatorio-categoria")
    fun relatorio(): List<CategoriaPerguntaView>{
        return perguntaService.relatorioCategoria()
    }

    @PostMapping
    @Transactional
    fun cadastrar(
        uriBuilder: UriComponentsBuilder,
        @RequestBody @Valid form: NovaPerguntaForm
    ): ResponseEntity<PerguntaView> {
        val novaPergunta = perguntaService.cadastrar(form)
        val uri = uriBuilder.path("/perguntas/${novaPergunta.id}").build().toUri()
        return ResponseEntity.created(uri).body(novaPergunta)
    }

    @PutMapping
    @Transactional
    fun alterar(
        @RequestBody @Valid form: AlteraPerguntaForm
    ):ResponseEntity<PerguntaView> {
        return ResponseEntity.ok(perguntaService.alterar(form))
    }

    @DeleteMapping("/{id}")
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun excluir(@PathVariable id: Long){
        perguntaService.deletar(id)
    }

}