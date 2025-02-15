package youduck.highton.util.ai

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class AI {
    @Value("\${ai.server.host}")
    private lateinit var host: String

    @Value("\${ai.server.port}")
    private lateinit var port: String

    private val restTemplate = RestTemplate()

    fun sendMessage(message: String): AIResponse {
        val url = "http://$host:$port/send-prompt"

        val headers =
            HttpHeaders().apply {
                contentType = MediaType.APPLICATION_JSON
            }

        val requestBody = mapOf("prompt" to message)

        val entity = HttpEntity(requestBody, headers)

        return restTemplate.postForObject(url, entity, AIResponse::class.java)
            ?: throw IllegalStateException("Failed to get response from AI server")
    }
}
