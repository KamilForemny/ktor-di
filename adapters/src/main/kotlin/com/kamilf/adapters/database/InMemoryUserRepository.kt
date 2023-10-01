package com.kamilf.adapters.database

import model.Email
import model.User
import port.UserRepository

class InMemoryUserRepository : UserRepository {
    private val users = mutableMapOf<Email, User>()

    override fun find(email: Email): User? = users[email]

    override fun save(user: User): User = user.also { users[user.email] = user }
}
