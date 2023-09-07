import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class RabbitMQListener(
    @Autowired private val endpointRegistry: RabbitListenerEndpointRegistry,
    var messageQueue: String = "") {

    val listenerContainer = endpointRegistry.getListenerContainer("quiz-pergunta-cadastrada")

    @RabbitListener(queues = ["quiz-pergunta-cadastrada"], autoStartup = "false")
    fun startListening(message: String) {
        messageQueue += message
    }
    fun iniciar() {
        if (!listenerContainer.isRunning)
            listenerContainer.start()
    }

    fun parar() {
        if (listenerContainer.isRunning)
            listenerContainer.stop()
    }
}
