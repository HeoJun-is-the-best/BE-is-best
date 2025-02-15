package youduck.highton.service.auth

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import youduck.highton.controller.auth.dto.LoginHttpDto
import youduck.highton.controller.auth.dto.RegisterHttpDto
import youduck.highton.entity.auth.User
import youduck.highton.repository.auth.AuthRepository

@Service
class AuthService(
    private val authRepository: AuthRepository,
    private val topicService: TopicService,
) {
    @Transactional
    fun register(request: RegisterHttpDto.Request): RegisterHttpDto.Response {
        if (authRepository.findByUsername(request.username) != null) {
            throw IllegalArgumentException("Username already exists")
        }

        val savedUser =
            authRepository.save(
                User(
                    username = request.username,
                    password = request.password,
                    name = request.name,
                ),
            )

        topicService.saveUserTopics(
            userId = savedUser.id,
            topics = request.topic,
        )

        return RegisterHttpDto.Response(
            userId = savedUser.id,
            username = savedUser.username,
            name = savedUser.name,
            message = "Successfully registered",
        )
    }

    @Transactional(readOnly = true)
    fun login(loginHttpDto: LoginHttpDto.Request): LoginHttpDto.Response {
        val user =
            authRepository.findByUsername(loginHttpDto.username)
                ?: throw IllegalArgumentException("Invalid username or password")

        if (user.password != loginHttpDto.password) {
            throw IllegalArgumentException("Invalid username or password")
        }

        return LoginHttpDto.Response(
            id = user.id,
            username = user.username,
            name = user.name,
        )
    }
}
