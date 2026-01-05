package server.project.domain.user.api

import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every

import io.mockk.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest
import org.springframework.graphql.test.tester.GraphQlTester
import server.project.domain.user.application.CreateUserService
import server.project.domain.user.application.GetUserService

import server.project.domain.user.api.dto.response.UserResponse

@GraphQlTest(UserController::class)
internal class UserControllerTest(
    @Autowired private val graphQlTester: GraphQlTester,
    @MockkBean private var createUserService: CreateUserService,
    @MockkBean private var getUserService: GetUserService
): BehaviorSpec( {


    Given("쿼리 테스트") {
        val userId = "abc"
        val expectedResponse = UserResponse(userId, "김호영", "hoyoung7827@gmail.com")

        every { getUserService.execute(userId) } returns expectedResponse

        When("getUser 쿼리를 실행하면") {
            val query = """
                query GetUser(${"$"}id: ID!) {
                    getUser(userId: ${"$"}id) {
                        userId
                        name
                        email
                    }
                }
            """.trimIndent()

            val response = graphQlTester.document(query)
                .variable("id", userId)
                .execute()

            Then("해당 사용자의 정보가 반환되어야 한다") {
                response.path("getUser.userId").entity(String::class.java).isEqualTo(userId)
                response.path("getUser.name").entity(String::class.java).isEqualTo("김호영")

                verify(exactly = 1) { getUserService.execute(userId) }
            }
        }
    }

    Given("새로운 사용자 정보가 주어졌을 때") {
        val inputName = "김호영"
        val inputEmail = "hoyoung7827@gmail.com"
        val expectedResponse = UserResponse("abc", inputName, inputEmail)

        every { createUserService.execute(any()) } returns expectedResponse

        When("createUser 뮤테이션을 실행하면") {
            val mutation = """
                mutation CreateUser(${"$"}name: String!, ${"$"}email: String!) {
                    createUser(input: { name: ${"$"}name, email: ${"$"}email }) {
                        userId
                        name
                        email
                    }
                }
            """.trimIndent()

            val response = graphQlTester.document(mutation)
                .variable("name", inputName)
                .variable("email", inputEmail)
                .execute()

            Then("생성된 유저 정보가 반환된다") {
                response.path("createUser.userId").entity(String::class.java).isEqualTo("abc")
                verify(exactly = 1) { createUserService.execute(any()) }
            }
        }
    }

})