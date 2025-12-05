package server.project.domain.user.application

import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import server.project.domain.user.api.dto.request.CreateUserRequest
import server.project.domain.user.api.dto.response.UserResponse
import server.project.domain.user.domain.User
import server.project.domain.user.domain.UserCreatedEvent
import server.project.domain.user.domain.UserRepository

@Service
class CreateUserService(
    private val userRepository: UserRepository,
    private val eventPublisher: ApplicationEventPublisher
    ) {

    @Transactional
    fun execute(request: CreateUserRequest): UserResponse {
        val user = User(
            name = request.name,
            email = request.email
        )

        val savedUser = userRepository.save(user)

        val message = UserResponse(savedUser.userId!!, savedUser.name, savedUser.email)
        eventPublisher.publishEvent(
            UserCreatedEvent(savedUser.userId!!, savedUser.name, savedUser.email))
        return message
    }
}