package youduck.highton.service.place

import org.springframework.stereotype.Service
import youduck.highton.controller.place.dto.PlaceRecommendHttpDto
import youduck.highton.util.ai.AI
import youduck.highton.util.ai.PlaceRecommendAi

@Service
class PlaceService(
    private val ai: AI,
) {
    fun getPlaceList(topics: String): List<PlaceRecommendHttpDto.Response> {
        val prompt = "한국에서 ${topics}로 유명한 곳은 뭐가 있어? 5곳 정도 address, description, latitude, longitude, name으로 작성해줘"

        val sendMessage = ai.sendMessage(prompt, PlaceRecommendAi::class)

        return sendMessage.response.map { placeInfo ->
            PlaceRecommendHttpDto.Response(
                name = placeInfo.name,
                address = placeInfo.address,
                latitude = placeInfo.latitude,
                longitude = placeInfo.longitude,
                description = placeInfo.description,
                thumbnail = "todo...",
            )
        }
    }
}
