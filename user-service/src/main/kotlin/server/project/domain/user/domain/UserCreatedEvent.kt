package server.project.domain.user.domain

data class UserCreatedEvent(
    val userId: String,
    val name: String,
    val email: String
)
