package server.project.domain.user.application

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.springframework.context.ApplicationEventPublisher
import org.springframework.test.util.ReflectionTestUtils
import server.project.domain.user.api.dto.request.CreateUserRequest
import server.project.domain.user.domain.User
import server.project.domain.user.domain.UserCreatedEvent
import server.project.domain.user.domain.UserRepository
import kotlin.test.Test

class CreateUserServiceTest {
    private val userRepository: UserRepository = mockk()
    private val eventPublisher: ApplicationEventPublisher = mockk(relaxed = true)

    private val createUserService = CreateUserService(userRepository, eventPublisher)

    @Test
    fun `유저 생성 성공 테스트`() {
        //given
        val request = CreateUserRequest("김호영", "hoyoung7827@gmail.com")

        val user = User(name = "김호영", email = "hoyoung7827@gmail.com")
        ReflectionTestUtils.setField(user, "userId", "abc")

        every { userRepository.save(any()) } returns user

        //when
        val response = createUserService.execute(request)

        //then
        verify(exactly = 1) {userRepository.save(any())}

        verify(exactly = 1) { eventPublisher.publishEvent(any<UserCreatedEvent>()) }

        assertThat(response.name).isEqualTo(request.name)
        assertThat(response.email).isEqualTo(request.email)
    }
}