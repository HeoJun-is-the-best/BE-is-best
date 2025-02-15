package youduck.highton.util.ai

data class AIResponse(
    val response: Any,
    val status_code: Int,
)

data class SubTopicGenerating(
    val response: List<String>,
    val status_code: Int,
)

data class PlaceRecommendAi(
    val response: List<PlaceInfo>,
    val status_code: Int,
) {
    data class PlaceInfo(
        val name: String,
        val address: String,
        val description: String,
        val latitude: Double,
        val longitude: Double,
    )
}

data class TrendingNewsResponse(
    val response: List<NewsItem>,
    val status_code: Int,
) {
    data class NewsItem(
        val category: String,
        val title: String,
        val description: String,
        val copyright: String,
    )
}
