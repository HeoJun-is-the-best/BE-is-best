package youduck.highton.controller.news.dto

class NewsHttpDto {
    data class Request(
        val subTopics: List<String>,
    )

    data class Response(
        val category: String,
        val title: String,
        val description: String,
        val copyright: String,
        val image: String,
    )
}
