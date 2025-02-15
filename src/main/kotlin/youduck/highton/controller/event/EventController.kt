package youduck.highton.controller.event

import org.springframework.web.bind.annotation.*
import youduck.highton.controller.event.dto.EventHttpDto
import youduck.highton.service.event.EventService
import youduck.highton.entity.event.Event

@RestController
@RequestMapping("/api/v1")
class EventController(
    private val eventService: EventService
) {
    @PostMapping("/events")
    fun getFeaturedEvents(
        @RequestBody request: EventHttpDto.Request,
    ): List<EventHttpDto.Response> {
        return eventService.getFeaturedSportEvents(request)
    }
}
