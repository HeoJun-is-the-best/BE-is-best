package youduck.highton.util.image

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.time.LocalDateTime

@Component
class IMAGE {
    @Value("\${image.server.host}")
    private lateinit var host: String

    @Value("\${image.server.port}")
    private lateinit var port: String

    private val restTemplate = RestTemplate()
    private val objectMapper = ObjectMapper().registerKotlinModule()

    companion object {
        private const val DEFAULT_IMAGE_URL = "https://images.unsplash.com/photo-1495020689067-958852a7765e"
    }

    // 단일 쿼리용 메서드 (기존 코드와의 호환성을 위해 유지)
    fun searchImages(query: String): ImageSearchResponse {
        val results = searchMultipleImages(listOf(query))
        return results.firstOrNull() ?: ImageSearchResponse(
            image_urls = listOf(DEFAULT_IMAGE_URL),
            query = query,
            timestamp = LocalDateTime.now(),
            total_images_found = 1
        )
    }

    // 여러 쿼리를 한 번에 처리하는 새로운 메서드
    fun searchMultipleImages(queries: List<String>): List<ImageSearchResponse> {
        return try {
            val url = "http://$host:$port/search"

            val headers = HttpHeaders().apply {
                contentType = MediaType.APPLICATION_JSON
            }

            val requestBody = mapOf("queries" to queries)
            val entity = HttpEntity(requestBody, headers)

            val response = restTemplate.postForObject(url, entity, ImageSearchWrapper::class.java)
                ?: throw IllegalStateException("Response body is null")

            // Convert results to ImageSearchResponse objects
            response.results.map { result ->
                ImageSearchResponse(
                    image_urls = result.image_urls.ifEmpty { listOf(DEFAULT_IMAGE_URL) },
                    query = result.query,
                    timestamp = LocalDateTime.parse(response.timestamp),
                    total_images_found = result.total_images_found
                )
            }
        } catch (e: Exception) {
            println("Batch image search failed: ${e.message}")
            // Return default responses for all queries
            queries.map { query ->
                ImageSearchResponse(
                    image_urls = listOf(DEFAULT_IMAGE_URL),
                    query = query,
                    timestamp = LocalDateTime.now(),
                    total_images_found = 1
                )
            }
        }
    }
}