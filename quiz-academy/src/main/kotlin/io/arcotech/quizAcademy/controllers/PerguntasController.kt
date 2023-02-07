package io.arcotech.quizAcademy.controllers

import io.arcotech.quizAcademy.models.Pergunta
import io.arcotech.quizAcademy.services.PerguntaService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
}