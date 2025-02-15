package youduck.highton.controller.topic

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import youduck.highton.controller.topic.dto.TopicHttpDto
import youduck.highton.service.auth.TopicService

@RestController
@RequestMapping("/api/v1")
class TopicController(
    private val topicService: TopicService,
) {
    @GetMapping("/topics/{userId}")
    fun getTopicMappingsByUserId(
        @PathVariable userId: Long,
    ): ResponseEntity<TopicHttpDto.Response> {
        val topicMappings = topicService.getTopicMappingsByUserId(userId)

        val response =
            TopicHttpDto.Response(
                topics =
                    topicMappings.groupBy { it.majorTopicId }.map { (majorTopicId, mappings) ->
                        val majorTopic = topicService.getMajorTopicById(majorTopicId)
                        TopicHttpDto.MajorTopicDto(
                            id = majorTopicId,
                            title = majorTopic.title,
                            subTopics =
                                mappings.map { mapping ->
                                    val subTopic = topicService.getSubTopicById(mapping.subTopicId)
                                    TopicHttpDto.SubTopicResponseDto(
                                        id = subTopic.id,
                                        majorTopicTitle = subTopic.title,
                                    )
                                },
                        )
                    },
            )

        return ResponseEntity.ok(response)
    }
}
