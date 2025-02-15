package youduck.highton.controller.topic.dto

class TopicHttpDto {
    data class Response(
        val topics: List<MajorTopicDto>,
    )

    data class MajorTopicDto(
        val id: Long,
        val title: String,
        val subTopics: List<SubTopicResponseDto>,
    )

    data class SubTopicResponseDto(
        val id: Long,
        val majorTopicTitle: String,
    )
}
