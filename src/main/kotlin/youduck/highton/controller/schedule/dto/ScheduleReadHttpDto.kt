package youduck.highton.controller.schedule.dto

import java.time.LocalDate

class ScheduleReadHttpDto {
    data class Request(
        val userId: Long,
        val year: Long,
        val month: Long,
    )

    data class Response(
        val id: Long,
        val title: String,
        val description: String,
        val startDate: LocalDate,
        val endDate: LocalDate,
    )
}
