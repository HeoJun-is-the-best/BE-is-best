package youduck.highton.service.event

import org.springframework.stereotype.Service
import youduck.highton.controller.event.dto.EventHttpDto
import youduck.highton.util.ai.AI
import youduck.highton.util.ai.SportEventAIResponse
import youduck.highton.util.image.IMAGE

@Service
class EventService(
    private val ai: AI,
    private val imageService: IMAGE,
) {
    fun getFeaturedSportEvents(request: EventHttpDto.Request): List<EventHttpDto.Response> {
        val prompt =
            """
            이번 달에 ${request.count}개의 주목할만한 ${request.topics} 행사를 알려줘. 
            각 이벤트에는 다음이 포함되어야 해.
            title(행사 제목)
            description(행사 설명, 짧게)
            startDate(행사 시작 일자(시간은 포함하지 않음))
            endDate(행사 종료 일자(시간은 포함하지 않음))
            location(위치(주소))

            응답을 JSON 배열의 이벤트 배열로 작성하고 value들은 모두 한국어야 해.
            """.trimIndent()

        val aiResponse = ai.sendMessage(prompt, SportEventAIResponse::class)

        // 이미지 검색을 위한 쿼리 리스트 생성
        val imageQueries = aiResponse.response.map { it.title }

        // 한 번의 요청으로 모든 이미지 검색
        val imageResponses = imageService.searchMultipleImages(imageQueries)

        return aiResponse.response.mapIndexed { index, eventInfo ->
            EventHttpDto.Response(
                title = eventInfo.title,
                description = eventInfo.description,
                startDate = eventInfo.startDate.slice(2..9).replace("-", "."),
                endDate = eventInfo.endDate.slice(2..9).replace("-", "."),
                location = eventInfo.location,
                thumbnail = imageResponses[index].image_urls.firstOrNull() ?: "",
            )
        }
    }
}
