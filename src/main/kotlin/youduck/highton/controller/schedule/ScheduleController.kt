package youduck.highton.controller.schedule

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import youduck.highton.controller.schedule.dto.ScheduleHttpDto
import youduck.highton.service.schedule.ScheduleService

@RestController
@RequestMapping("/api/v1")
class ScheduleController(
    private val scheduleService: ScheduleService,
) {
    @PostMapping("/schedules/save")
    fun save(
        @RequestBody request: ScheduleHttpDto.Request,
    ): ScheduleHttpDto.Response {
        return scheduleService.save(request)
    }
}
