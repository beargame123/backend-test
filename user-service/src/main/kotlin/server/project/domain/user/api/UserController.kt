package server.project.domain.user.api

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import server.project.domain.user.api.dto.request.CreateUserRequest
import server.project.domain.user.api.dto.response.UserResponse
import server.project.domain.user.application.CreateUserService
import server.project.domain.user.application.GetUserService

@Controller
class UserController(
    private val createUserService: CreateUserService,
    private val getUserService: GetUserService
) {
    @MutationMapping
    fun createUser(
        @Argument("input") request: CreateUserRequest
    ): UserResponse{
        return createUserService.execute(request)
    }

    @QueryMapping
    fun getUser(@Argument userId: String): UserResponse {
        return getUserService.execute(userId)
    }
}