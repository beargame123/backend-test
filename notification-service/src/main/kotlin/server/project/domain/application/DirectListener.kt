package server.project.domain.application

import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import server.project.domain.api.dto.response.UserResponse

@Component
class DirectListener {
    private val log = LoggerFactory.getLogger(DirectListener::class.java)

    @RabbitListener(queues = ["direct_queue"])
    fun getMessage(response: UserResponse) {
        log.info("id={} 이고 name={}인 email={}에게 이메일 발송 완료", response.userId, response.name, response.email)
    }
}