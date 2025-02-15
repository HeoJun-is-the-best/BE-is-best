package youduck.highton.controller.event

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import youduck.highton.controller.event.dto.EventHttpDto
import youduck.highton.service.event.EventService

@RestController
@RequestMapping("/api/v1")
class EventController(
    private val eventService: EventService,
) {
    @PostMapping("/events")
    fun getFeaturedEvents(
        @RequestBody request: EventHttpDto.Request,
    ): List<EventHttpDto.Response> {
        return eventService.getFeaturedSportEvents(request)
    }
}
