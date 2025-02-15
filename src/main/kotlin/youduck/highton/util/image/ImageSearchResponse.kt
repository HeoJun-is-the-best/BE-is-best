package youduck.highton.util.image

import java.time.LocalDateTime

data class ImageSearchResponse(
    val image_urls: List<String>,
    val query: String,
    val timestamp: LocalDateTime,
    val total_images_found: Int,
)

// New wrapper class to match Flask server response format
data class ImageSearchWrapper(
    val results: List<ImageSearchResult>,
    val timestamp: String,
)

data class ImageSearchResult(
    val query: String,
    val image_urls: List<String>,
    val total_images_found: Int,
)
