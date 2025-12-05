package server.project.domain.user.application

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import server.project.domain.user.api.dto.response.UserResponse
import server.project.domain.user.domain.User
import server.project.domain.user.domain.UserRepository
import java.util.Optional

class GetUserServiceTest {
    private val userRepository: UserRepository = mockk()
    private val getUserService: GetUserService = GetUserService(userRepository)

    private val expectedUserResponse = UserResponse("693199eca4b651538b637b51", "김호영", "hoyoung7827@gmail.com")

    @Test
    fun `userId로 찾기 성공 테스트`() {
        //given
        val userId = "693199eca4b651538b637b51"
        every { userRepository.findUserByUserId(userId) } returns expectedUserResponse

        //when
        val result = getUserService.execute(userId)

        //then
        verify(exactly = 1) { userRepository.findUserByUserId(userId)  }
        assertThat(result).isEqualTo(expectedUserResponse)
    }
}