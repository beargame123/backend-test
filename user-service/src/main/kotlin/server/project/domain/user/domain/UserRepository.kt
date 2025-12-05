package server.project.domain.user.domain

import org.springframework.data.mongodb.repository.MongoRepository
import server.project.domain.user.api.dto.response.UserResponse

interface UserRepository: MongoRepository<User, String> {
    fun findUserByUserId(userId: String): UserResponse
}