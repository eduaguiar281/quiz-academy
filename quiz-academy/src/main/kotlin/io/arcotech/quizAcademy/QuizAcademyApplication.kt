package io.arcotech.quizAcademy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
class QuizAcademyApplication

fun main(args: Array<String>) {
	runApplication<QuizAcademyApplication>(*args)
}
