package youduck.highton.repository.topic

import org.springframework.data.jpa.repository.JpaRepository
import youduck.highton.entity.topic.TopicMapping

interface TopicMappingRepository : JpaRepository<TopicMapping, Long>
