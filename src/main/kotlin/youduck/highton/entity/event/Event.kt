package youduck.highton.entity.event

import jakarta.persistence.Entity
import youduck.highton.entity.BaseEntity

@Entity
data class Event (
    val title: String,
    val description: String,
    val startDate: String,
    val endDate: String,
    val location: String,
): BaseEntity()