package youduck.highton.util.ai

data class AIResponse(
    val response: Any,
    val status_code: Int,
)

data class SubTopicGeneratingResponse(
    val sports_subcategories: List<String>,
)

data class SubTopicGenerating(
    val response: List<String>,
    val status_code: Int,
) {
    companion object {
        fun fromRawResponse(aiResponse: AIResponse): SubTopicGenerating {
            val rawResponse = aiResponse.response as Map<*, *>
            val subcategories = (rawResponse["sports_subcategories"] as List<*>).map { it.toString() }
            return SubTopicGenerating(
                response = subcategories,
                status_code = aiResponse.status_code,
            )
        }
    }
}

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

data class SportEventAIResponse(
    val response: List<SportEventInfo>,
    val status_code: Int,
) {
    data class SportEventInfo(
        val title: String,
        val description: String,
        val startDate: String,
        val endDate: String,
        val location: String,
    )
}
