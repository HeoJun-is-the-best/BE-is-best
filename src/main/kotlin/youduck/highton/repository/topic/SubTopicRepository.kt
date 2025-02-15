package youduck.highton.repository.topic

import org.springframework.data.jpa.repository.JpaRepository
import youduck.highton.entity.topic.SubTopic

interface SubTopicRepository : JpaRepository<SubTopic, Long> {
    fun findByTitle(title: String): SubTopic?
}
