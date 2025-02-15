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

    fun getPlaceList(topics: String): List<PlaceRecommendHttpDto.Response> {
        val prompt = "한국에서 ${topics}로 유명한 곳은 뭐가 있어? 5곳 정도 address, description, latitude, longitude, name으로 작성해줘"

        val aiResponse = ai.sendMessage(prompt, PlaceRecommendAi::class)

        // 모든 장소에 대한 이미지 쿼리 준비
        val imageQueries =
            aiResponse.response.map { placeInfo ->
                "${placeInfo.name} ${placeInfo.address}".replace(" ", "-")
                    .replace("(", "")
                    .replace(")", "")
            }

        // 한 번의 요청으로 모든 이미지 검색
        val imageResults =
            try {
                image.searchMultipleImages(imageQueries)
                    .associateBy { it.query } // 쿼리를 키로 하는 맵으로 변환
            } catch (e: Exception) {
                println("이미지 일괄 검색 중 오류 발생: ${e.message}")
                emptyMap()
            }

        // 장소 정보와 이미지 결과를 매핑
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
