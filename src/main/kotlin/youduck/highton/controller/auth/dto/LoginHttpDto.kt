package youduck.highton.controller.auth.dto

class LoginHttpDto {
    data class Request(
        val username: String,
        val password: String,
    )

    data class Response(
        val id: Long,
        val name: String,
        val username: String,
    )
}
