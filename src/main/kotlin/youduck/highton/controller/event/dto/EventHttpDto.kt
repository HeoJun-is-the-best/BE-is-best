package youduck.highton.controller.event.dto

class EventHttpDto {
    data class Request(
        val topics: String,
        val count: Int,
    )

    data class Response(
        val title: String,
        val description: String,
        val startDate: String,
        val endDate: String,
        val location: String,
        val thumbnail: String,
    )
}
