package io.arcotech.quizAcademy.messages

import org.springframework.amqp.core.Queue
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbiMQConfig {

    @Bean
    fun filaQuizPerguntaCadastrada(): Queue{
        return Queue("quiz-pergunta-cadastrada", true)
    }
    @Bean
    fun filaQuizPerguntaAlterada(): Queue{
        return Queue("quiz-pergunta-alterada", true)
    }

    @Bean
    fun filaQuizPerguntaExcluida(): Queue{
        return Queue("quiz-pergunta-excluida", true)
    }
}