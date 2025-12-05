package server.project.domain.user.api

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every

import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest
import org.springframework.graphql.test.tester.GraphQlTester
import server.project.domain.user.application.CreateUserService
import server.project.domain.user.application.GetUserService

import server.project.domain.user.api.dto.response.UserResponse

@GraphQlTest(UserController::class)
class UserControllerTest(
    @Autowired private val graphQlTester: GraphQlTester
) {

    @MockkBean
    private lateinit var createUserService: CreateUserService

    @MockkBean
    private lateinit var getUserService: GetUserService

    @Test
    fun `쿼리 테스트`() {
        // given
        val userId = "abc"
        val expectedResponse = UserResponse(userId, "김호영", "hoyoung7827@gmail.com")

        every { getUserService.execute(userId) } returns expectedResponse

        // when
        val query = """
            query {
                getUser(userId: "$userId") {
                    userId
                    name
                    email
                }
            }
        """

        // then
        graphQlTester.document(query)
            .execute()
            .path("getUser.userId").entity(String::class.java).isEqualTo(userId)
            .path("getUser.name").entity(String::class.java).isEqualTo("김호영")
            .path("getUser.email").entity(String::class.java).isEqualTo("hoyoung7827@gmail.com")

        verify(exactly = 1) { getUserService.execute(userId) }
    }

    @Test
    fun `뮤테이션 테스트`() {
        // given
        val inputName = "김호영"
        val inputEmail = "hoyoung7827@gmail.com"
        val expectedResponse = UserResponse("abc", inputName, inputEmail)

        every { createUserService.execute(any()) } returns expectedResponse

        // when
        val query = """
            mutation {
                createUser(input: {
                    name: "$inputName",
                    email: "$inputEmail"
                }) {
                    userId
                    name
                    email
                }
            }
        """

        // then
        graphQlTester.document(query)
            .execute()
            .path("createUser.userId").entity(String::class.java).isEqualTo("abc")
            .path("createUser.name").entity(String::class.java).isEqualTo(inputName)
            .path("createUser.email").entity(String::class.java).isEqualTo(inputEmail)

        verify(exactly = 1) { createUserService.execute(any()) }
    }
}