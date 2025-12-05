package server.project.domain.user.application

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import server.project.domain.user.api.dto.response.UserResponse
import server.project.domain.user.domain.UserRepository

@Service
class GetUserService(
    private val userRepository: UserRepository
) {
    @Transactional(readOnly = true)
    fun execute(userId: String): UserResponse {
        val user = userRepository.findUserByUserId(userId)
        return UserResponse(user.userId, user.name, user.email)
    }
}