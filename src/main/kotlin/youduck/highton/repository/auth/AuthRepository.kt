package youduck.highton.repository.auth

import org.springframework.data.jpa.repository.JpaRepository
import youduck.highton.entity.auth.User

interface AuthRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): User?
}
