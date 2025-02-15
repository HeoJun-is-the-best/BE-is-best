package youduck.highton.service.place

import org.springframework.stereotype.Service
import youduck.highton.controller.place.dto.PlaceRecommendHttpDto
import youduck.highton.util.ai.AI
import youduck.highton.util.ai.PlaceRecommendAi
import youduck.highton.util.image.IMAGE

@Service
class PlaceService(
    private val ai: AI,
    private val image: IMAGE,
) {
    companion object {
        private const val DEFAULT_IMAGE_URL = "https://images.unsplash.com/photo-1495020689067-958852a7765e"
    }

    fun getPlaceList(topics: PlaceRecommendHttpDto.Request): List<PlaceRecommendHttpDto.Response> {
        val prompt =
            "한국에서 ${topics.topics}로 유명한 곳은 뭐가 있어?" +
                "${topics.count}곳 정도 address, description, latitude, longitude, name으로 작성해줘"

        val aiResponse = ai.sendMessage(prompt, PlaceRecommendAi::class)

        val imageQueries =
            aiResponse.response.map { placeInfo ->
                "${placeInfo.name} ${placeInfo.address}".replace(" ", "-")
                    .replace("(", "")
                    .replace(")", "")
            }

        val imageResults =
            try {
                image.searchMultipleImages(imageQueries)
                    .associateBy { it.query }
            } catch (e: Exception) {
                println("이미지 일괄 검색 중 오류 발생: ${e.message}")
                emptyMap()
            }

        return aiResponse.response.mapIndexed { index, placeInfo ->
            val query = imageQueries[index]
            val imageResult = imageResults[query]

            PlaceRecommendHttpDto.Response(
                name = placeInfo.name,
                address = placeInfo.address,
                latitude = placeInfo.latitude,
                longitude = placeInfo.longitude,
                description = placeInfo.description,
                thumbnail = imageResult?.image_urls?.randomOrNull() ?: DEFAULT_IMAGE_URL,
            )
        }
    }
}
