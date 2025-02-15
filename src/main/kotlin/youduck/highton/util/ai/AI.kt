package youduck.highton.util.ai

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import kotlin.reflect.KClass
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
    private val objectMapper = ObjectMapper().registerKotlinModule()

    // Class 파라미터가 없는 경우 AIResponse 반환
    fun sendMessage(message: String): AIResponse {
        val rawResponse = sendRequestToServer(message)
        return objectMapper.readValue(rawResponse, AIResponse::class.java)
    }

    // Class 파라미터가 있는 경우 해당 타입으로 변환하여 반환
    fun <T : Any> sendMessage(message: String, responseClass: KClass<T>): T {
        val rawResponse = sendRequestToServer(message)
        return objectMapper.readValue(rawResponse, responseClass.java)
    }

    // HTTP 요청 공통 로직
    private fun sendRequestToServer(message: String): String {
        val url = "http://$host:$port/send-prompt"

        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
        }

        val requestBody = mapOf("prompt" to message)
        val entity = HttpEntity(requestBody, headers)

        // String으로 응답 받기
        val rawResponse = restTemplate.postForObject(url, entity, String::class.java)
            ?: throw IllegalStateException("Response body is null")

        // 원본 JSON 출력
        println("=== Raw JSON Response ===")
        println(rawResponse)
        println("========================")

        return rawResponse
    }
}