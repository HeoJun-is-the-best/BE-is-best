package youduck.highton.service.schedule

import org.springframework.stereotype.Service
import youduck.highton.controller.schedule.dto.ScheduleReadHttpDto
import youduck.highton.controller.schedule.dto.ScheduleSaveHttpDto
import youduck.highton.entity.schedule.Schedule
import youduck.highton.repository.schedule.ScheduleRepository

@Service
class ScheduleService(
    private val scheduleRepository: ScheduleRepository,
) {
    fun save(request: ScheduleSaveHttpDto.Request): ScheduleSaveHttpDto.Response {
        val entity =
            Schedule(
                userId = request.userId,
                title = request.title,
                description = request.description,
                startDate = request.startDate,
                endDate = request.endDate,
            )

        scheduleRepository.save(entity)
        return ScheduleSaveHttpDto.Response(
            id = entity.id,
            title = entity.title,
            description = entity.description,
            startDate = entity.startDate,
            endDate = entity.endDate,
        )
    }

    fun read(request: ScheduleReadHttpDto.Request): List<ScheduleReadHttpDto.Response> {
        return scheduleRepository.findAllByUserId(request.userId)
            .map { schedule ->
                ScheduleReadHttpDto.Response(
                    id = schedule.id,
                    title = schedule.title,
                    description = schedule.description,
                    startDate = schedule.startDate,
                    endDate = schedule.endDate,
                )
            }
    }
}
