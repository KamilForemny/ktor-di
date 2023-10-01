package com.kamilf

import model.Email
import model.Registration
import model.User
import port.RegistrationRepository
import port.UserRepository

class UseCaseTestSetup {
    val userRepository: UserRepository = UserRepositoryStub()
    val registrationRepository: RegistrationRepository = RegistrationRepositoryStub()
}

class RegistrationRepositoryStub : RegistrationRepository {
    private val registrations = mutableMapOf<Email, Registration>()

    override fun save(registration: Registration): Registration =
        registration.also { registrations[registration.email] = registration }

    override fun find(email: Email): Registration? = registrations[email]

    override fun exists(email: Email): Boolean = registrations.containsKey(email)
}

class UserRepositoryStub : UserRepository {
    private val users = mutableMapOf<Email, User>()

    override fun find(email: Email): User? = users[email]

    override fun save(user: User): User = user.also { users[user.email] = user }
}
