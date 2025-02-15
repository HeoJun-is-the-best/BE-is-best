package youduck.highton.entity.topic

import jakarta.persistence.Entity
import youduck.highton.entity.BaseEntity

@Entity
data class TopicMapping(
    val userId: Long,
    val majorTopicId: Long,
    val subTopicId: Long,
) : BaseEntity()
