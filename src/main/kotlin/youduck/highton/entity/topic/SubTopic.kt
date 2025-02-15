package youduck.highton.entity.topic

import jakarta.persistence.Entity
import youduck.highton.entity.BaseEntity

@Entity
data class SubTopic(
    val title: String,
) : BaseEntity()
