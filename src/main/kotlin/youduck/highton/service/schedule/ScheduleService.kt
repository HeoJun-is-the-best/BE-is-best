package youduck.highton.service.schedule

import org.springframework.stereotype.Service
import youduck.highton.controller.schedule.dto.ScheduleHttpDto
import youduck.highton.entity.schedule.Schedule
import youduck.highton.repository.schedule.ScheduleRepository

@Service
class ScheduleService(
    private val scheduleRepository: ScheduleRepository,
) {
    fun save(request: ScheduleHttpDto.Request): ScheduleHttpDto.Response {
        val entity =
            Schedule(
                userId = request.userId,
                title = request.title,
                description = request.description,
                startDate = request.startDate,
                endDate = request.endDate,
            )

        scheduleRepository.save(entity)
        return ScheduleHttpDto.Response(
            id = entity.id,
            title = entity.title,
            description = entity.description,
            startDate = entity.startDate,
            endDate = entity.endDate,
        )
    }
}
