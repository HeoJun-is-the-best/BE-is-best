package youduck.highton.entity.auth

import jakarta.persistence.Entity
import youduck.highton.entity.BaseEntity

@Entity
data class User(
    val username: String,
    val password: String,
) : BaseEntity()
