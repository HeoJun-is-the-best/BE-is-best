package youduck.highton.util.ai

data class AIResponse(
    val response: Response,
    val statusCode: Int,
)

data class Response(
    val message: String,
)
