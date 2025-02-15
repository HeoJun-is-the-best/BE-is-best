package youduck.highton.util.ai

data class AIResponse(
    val response: Any,
    val status_code: Int,
)

data class SubTopicGenerating(
    val response: List<String>,
    val status_code: Int,
)