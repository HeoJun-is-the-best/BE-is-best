package youduck.highton.service.news

import org.springframework.stereotype.Service
import youduck.highton.controller.news.dto.NewsHttpDto
import youduck.highton.util.ai.AI
import youduck.highton.util.ai.TrendingNewsResponse
import youduck.highton.util.image.IMAGE

@Service
class NewsService(
    private val ai: AI,
    private val image: IMAGE
) {
    companion object {
        private const val DEFAULT_IMAGE_URL = "https://images.unsplash.com/photo-1495020689067-958852a7765e"
    }

    fun getTrendingNews(subTopics: List<String>): List<NewsHttpDto.Response> {
        val prompt = buildPrompt(subTopics)
        val aiResponse = ai.sendMessage(
            message = prompt,
            responseClass = TrendingNewsResponse::class
        )

        // 모든 카테고리에 대한 쿼리를 준비
        val imageQueries = aiResponse.response.map { newsItem ->
            newsItem.category.replace(" ", "-")
                .replace("(", "")
                .replace(")", "")
        }

        // 한 번의 요청으로 모든 이미지 검색
        val imageResults = try {
            image.searchMultipleImages(imageQueries)
                .associateBy { it.query }  // 쿼리를 키로 하는 맵으로 변환
        } catch (e: Exception) {
            println("이미지 일괄 검색 중 오류 발생: ${e.message}")
            emptyMap()
        }

        // 뉴스 아이템과 이미지 결과를 매핑
        return aiResponse.response.mapIndexed { index, newsItem ->
            val query = imageQueries[index]
            val imageResult = imageResults[query]

            NewsHttpDto.Response(
                category = newsItem.category,
                title = newsItem.title,
                description = newsItem.description,
                copyright = newsItem.copyright,
                image = imageResult?.image_urls?.randomOrNull() ?: DEFAULT_IMAGE_URL
            )
        }
    }

    private fun buildPrompt(subTopics: List<String>): String {
        return """
            주제: ${subTopics.joinToString(", ")}에 대한 2025년 최신 트렌딩 뉴스를 생성해주세요.
            다음과 같은 JSON 형식으로 응답해주세요:
            "category": "카테고리",
            "title": "제목",
            "description": "설명",
            "copyright": "저작권"
            현재 유행하는 뉴스 3개를 생성해주세요.
        """.trimIndent()
    }
}