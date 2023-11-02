package io.arcotech.quizAcademy.controllers

import io.arcotech.quizAcademy.dto.AlteraPerguntaForm
import io.arcotech.quizAcademy.dto.CategoriaPerguntaView
import io.arcotech.quizAcademy.dto.NovaPerguntaForm
import io.arcotech.quizAcademy.dto.PerguntaView
import io.arcotech.quizAcademy.services.featuretoggles.FeatureToggleManagerService
import io.arcotech.quizAcademy.services.PerguntaService
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/perguntas")
class PerguntasController (val perguntaService: PerguntaService, val featureToggleManager: FeatureToggleManagerService) {

    @GetMapping
    fun listar(
        @RequestParam(required = false) autor: String?,
        @PageableDefault(size = 5, sort = ["categoria"], direction = Sort.Direction.ASC) paginacao: Pageable
    ): Page<PerguntaView> {
        return perguntaService.listar(autor, paginacao)
    }
    @GetMapping("/{id}")
    fun ObterPorId(@PathVariable id: Long): PerguntaView{
        return perguntaService.obterPorId(id);
    }

    @GetMapping("/relatorio-categoria")
    fun relatorio(@RequestHeader("x-user-id") userId: Int): List<CategoriaPerguntaView>{
        return perguntaService.relatorioCategoria(userId)
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