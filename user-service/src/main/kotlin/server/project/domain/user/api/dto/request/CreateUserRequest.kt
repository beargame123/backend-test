package server.project.domain.user.api.dto.request

data class CreateUserRequest (
    val name: String,
    val email: String,
)