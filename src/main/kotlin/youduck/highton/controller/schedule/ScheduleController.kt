package youduck.highton.controller.schedule

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import youduck.highton.controller.schedule.dto.ScheduleReadHttpDto
import youduck.highton.controller.schedule.dto.ScheduleSaveHttpDto
import youduck.highton.service.schedule.ScheduleService

@RestController
@RequestMapping("/api/v1")
class ScheduleController(
    private val scheduleService: ScheduleService,
) {
    @PostMapping("/schedules/save")
    fun save(
        @RequestBody request: ScheduleSaveHttpDto.Request,
    ): ScheduleSaveHttpDto.Response {
        return scheduleService.save(request)
    }

    @PostMapping("/schedules")
    fun getAll(
        @RequestBody request: ScheduleReadHttpDto.Request,
    ): List<ScheduleReadHttpDto.Response> {
        return scheduleService.read(request)
    }
}
