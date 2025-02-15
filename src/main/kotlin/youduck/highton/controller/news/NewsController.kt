package youduck.highton.controller.news

import org.springframework.web.bind.annotation.*
import youduck.highton.controller.news.dto.NewsHttpDto
import youduck.highton.service.news.NewsService

@RestController
@RequestMapping("/api/v1")
class NewsController(
    private val newsService: NewsService
) {
    @PostMapping("/news/trending")
    fun getTrendingNews(@RequestBody request: NewsHttpDto.Request): List<NewsHttpDto.Response> {
        return newsService.getTrendingNews(request.subTopics)
    }
}