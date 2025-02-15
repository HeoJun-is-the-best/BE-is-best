package youduck.highton.controller.auth.dto

class LoginHttpDto {
    data class Request(
        val username: String,
        val password: String,
    )

    data class Response(
        val username: String,
        val password: String,
        val name: String,
// TODO(Jun): 로그인 시 더 필요한 정보 없는지 확인해봐야 함
    )
}
