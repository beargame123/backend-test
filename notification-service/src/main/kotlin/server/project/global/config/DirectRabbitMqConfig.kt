package server.project.global.config

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.core.Queue
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class DirectRabbitMqConfig(
    @Value("\${rabbit.queue.name}")
    private val queueName: String,

    @Value("\${rabbit.exchange.name}")
    private val exchangeName: String,

    @Value("\${rabbit.routing.key}")
    private val routingKey: String
) {
    @Bean
    fun queue(): Queue = Queue(queueName)

    @Bean
    fun directExchange(): DirectExchange = DirectExchange(exchangeName)

    @Bean
    fun binding(queue: Queue, exchange: DirectExchange): Binding {
        return BindingBuilder.bind(queue)
            .to(exchange)
            .with(routingKey)
    }
}