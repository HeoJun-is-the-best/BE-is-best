package youduck.highton.entity.topic

import jakarta.persistence.Entity
import youduck.highton.entity.BaseEntity

@Entity
data class MajorTopic(
    val title: String,
) : BaseEntity()
