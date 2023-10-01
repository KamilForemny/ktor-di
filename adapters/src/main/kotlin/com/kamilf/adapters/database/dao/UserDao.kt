package com.kamilf.adapters.database.dao

import java.util.*

data class UserDao(
    val id: UUID,
    val email: String,
    val password: String,
    val status: UserStatus,
)

enum class UserStatus {
    ACTIVE,
    INACTIVE,
    ;
}
