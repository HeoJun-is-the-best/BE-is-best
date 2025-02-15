package youduck.highton.entity.schedule

import jakarta.persistence.Entity
import youduck.highton.entity.BaseEntity
import java.time.LocalDate

@Entity
data class Schedule(
    val userId: Long,
    val title: String,
    val description: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
) : BaseEntity()
