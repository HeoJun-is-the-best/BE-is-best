package youduck.highton.controller.topic.dto

class SubTopicGenerativeAIHttpDto {
    data class Request(
        val majorTopic: String,
    )

    data class Response(
        val subTopic: List<String>,
    )
}
