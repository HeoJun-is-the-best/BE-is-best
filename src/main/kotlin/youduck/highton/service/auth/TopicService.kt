package youduck.highton.service.auth

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import youduck.highton.controller.auth.dto.RegisterHttpDto
import youduck.highton.entity.topic.MajorTopic
import youduck.highton.entity.topic.SubTopic
import youduck.highton.entity.topic.TopicMapping
import youduck.highton.repository.topic.MajorTopicRepository
import youduck.highton.repository.topic.SubTopicRepository
import youduck.highton.repository.topic.TopicMappingRepository
import youduck.highton.util.ai.AI
import youduck.highton.util.ai.SubTopicGenerating

@Service
class TopicService(
    private val majorTopicRepository: MajorTopicRepository,
    private val subTopicRepository: SubTopicRepository,
    private val topicMappingRepository: TopicMappingRepository,
    private val ai: AI,
) {
    // 회원가입 시 Topic 저장 및 매핑
    @Transactional
    fun saveUserTopics(
        userId: Long,
        topics: List<RegisterHttpDto.Request.TopicMappingDto>,
    ) {
        topics.forEach { topicMapping ->
            // 1. Major Topic 저장 또는 조회
            val majorTopic =
                majorTopicRepository.findByTitle(topicMapping.majorTopic)
                    ?: saveMajorTopic(topicMapping.majorTopic)

            // 2. 각 Sub Topic 저장 또는 조회 후 매핑
            topicMapping.subTopic.forEach { subTopicTitle ->
                val subTopic =
                    subTopicRepository.findByTitle(subTopicTitle)
                        ?: saveSubTopic(subTopicTitle)

                // 3. Topic Mapping 생성
                saveTopicMapping(
                    userId = userId,
                    majorTopicId = majorTopic.id,
                    subTopicId = subTopic.id,
                )
            }
        }
    }

    // Major Topic 저장
    private fun saveMajorTopic(title: String) = majorTopicRepository.save(MajorTopic(title = title))

    // Sub Topic 저장
    private fun saveSubTopic(title: String) = subTopicRepository.save(SubTopic(title = title))

    // Topic Mapping 저장
    private fun saveTopicMapping(
        userId: Long,
        majorTopicId: Long,
        subTopicId: Long,
    ) = topicMappingRepository.save(
        TopicMapping(
            userId = userId,
            majorTopicId = majorTopicId,
            subTopicId = subTopicId,
        ),
    )

    // 모든 Major Topic 조회
    fun getAllMajorTopics() = majorTopicRepository.findAll()

    // 모든 Sub Topic 조회
    fun getAllSubTopics() = subTopicRepository.findAll()

    // 특정 사용자의 모든 Topic Mapping 조회
    fun getTopicMappingsByUserId(userId: Long): List<TopicMapping> =
        topicMappingRepository.findAll()
            .filter { it.userId == userId }

    // Major Topic ID로 조회
    fun getMajorTopicById(id: Long): MajorTopic =
        majorTopicRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Major Topic not found with id: $id") }

    // Sub Topic ID로 조회
    fun getSubTopicById(id: Long): SubTopic =
        subTopicRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Sub Topic not found with id: $id") }

    // AI에 주제를 전송하고 응답을 받는 메서드
    fun getAIResponseForTopic(topic: String): SubTopicGenerating? {
        val prompt = "$topic 카테고리에 부주제로는 뭐가 있을까? 5개 정도 배열로 보내줘"
        return ai.sendMessage(prompt, SubTopicGenerating::class)
    }
}
