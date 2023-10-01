package com.kamilf.adapters.database

import model.Email
import model.Registration
import port.RegistrationRepository

class InMemoryRegistrationRepository : RegistrationRepository {
    private val registrations = mutableMapOf<Email, Registration>()

    override fun save(registration: Registration): Registration =
        registration.also { registrations[registration.email] = registration }

    override fun find(email: Email): Registration? = registrations[email]

    override fun exists(email: Email): Boolean = registrations.containsKey(email)
}
