package youduck.highton.repository.schedule

import org.springframework.data.jpa.repository.JpaRepository
import youduck.highton.entity.schedule.Schedule

interface ScheduleRepository : JpaRepository<Schedule, Long> {
    fun findAllByUserId(userId: Long): List<Schedule>
}
