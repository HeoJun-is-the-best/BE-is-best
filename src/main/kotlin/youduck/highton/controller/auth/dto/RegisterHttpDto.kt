package youduck.highton.controller.auth.dto

class RegisterHttpDto {
    data class Request(
        val username: String,
        val password: String,
        val topic: List<TopicMappingDto>,
    ) {
        data class TopicMappingDto(
            val majorTopic: String,
            val subTopic: List<String>,
        )
    }

    data class Response(
        val userId: Long,
        val username: String,
        val message: String,
    )
}
