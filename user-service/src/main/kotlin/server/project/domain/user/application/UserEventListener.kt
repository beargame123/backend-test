package server.project.domain.user.application

import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener
import server.project.domain.user.api.dto.response.UserResponse
import server.project.domain.user.domain.UserCreatedEvent

@Component
class UserEventListener(
    private val rabbitTemplate: RabbitTemplate,

    @Value("\${rabbit.exchange.name}")
    private val exchangeName: String,

    @Value("\${rabbit.routing.key}")
    private val routingKey: String
) {
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun sendMq(event: UserCreatedEvent) {
        val message = UserResponse(event.userId, event.name, event.email)

        rabbitTemplate.convertAndSend(exchangeName, routingKey, message)
    }
}