package youduck.highton.controller.schedule.dto

import java.time.LocalDate

class ScheduleHttpDto {
    data class Request(
        val userId: Long,
        val title: String,
        val description: String,
        val startDate: LocalDate,
        val endDate: LocalDate,
    )

    data class Response(
        val id: Long,
        val title: String,
        val description: String,
        val startDate: LocalDate,
        val endDate: LocalDate,
    )
}
