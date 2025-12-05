package server.project.domain.user.application

import io.mockk.mockk
import io.mockk.verify
import org.springframework.amqp.rabbit.core.RabbitTemplate
import server.project.domain.user.api.dto.response.UserResponse
import server.project.domain.user.domain.UserCreatedEvent
import kotlin.test.Test

class UserEventListenerTest {
    private val rabbitTemplate: RabbitTemplate = mockk(relaxed = true)

    private val exchangeName = "test-exchange"
    private val routingKey = "test-routing-key"

    private val userEventListener = UserEventListener(rabbitTemplate, exchangeName, routingKey)

    @Test
    fun `MQ 메세지 전송 성공 테스트`() {
        //given
        val event = UserCreatedEvent(
            "123xaf123n",
            "김호영",
            "hoyoung7827@gmail.com"
        )

        val expectedMessage = UserResponse(
            event.userId,
            event.name,
            event.email
        )

        //when
        userEventListener.sendMq(event)

        //then
        verify(exactly = 1) {
            rabbitTemplate.convertAndSend(exchangeName, routingKey, expectedMessage)
        }
    }
}