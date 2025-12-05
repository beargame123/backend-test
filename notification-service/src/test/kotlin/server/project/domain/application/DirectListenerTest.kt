package server.project.domain.application

import io.mockk.verify
import server.project.domain.api.dto.response.UserResponse
import kotlin.test.Test

class DirectListenerTest {
    private val directListener = DirectListener()

    @Test
    fun `메시지 수신 및 로그 출력 성공 테스트`() {
        //given
        val response = UserResponse("693199eca4b651538b637b51", "김호영", "hoyoung7827@gmail.com")

        //when & then
        directListener.getMessage(response)
    }
}