package youduck.highton.repository.topic

import org.springframework.data.jpa.repository.JpaRepository
import youduck.highton.entity.topic.MajorTopic

interface MajorTopicRepository : JpaRepository<MajorTopic, Long> {
    fun findByTitle(title: String): MajorTopic?
}
