package youduck.highton.controller.place.dto

class PlaceRecommendHttpDto {
    data class Request(
        val topics: String,
    )

    data class Response(
        val name: String,
        val address: String,
        val latitude: Double,
        val longitude: Double,
        val description: String,
        val thumbnail: String,
    )
}
