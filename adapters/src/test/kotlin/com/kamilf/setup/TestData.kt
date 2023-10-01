package com.kamilf.setup

import com.kamilf.adapters.web.response.RegisterResponse
import io.kotest.matchers.shouldBe
import io.ktor.client.call.*
import io.ktor.http.*

object TestData {
    val email = randomAlphanumeric() + "@test.com"
    val password = randomAlphanumeric(12)

    fun randomAlphanumeric(length: Int = 8): String {
        val charPool = listOf('a'..'z') + ('A'..'Z') + ('0'..'9')
        return List(length) { charPool.random() }.joinToString("")
    }

    suspend fun givenActiveUser(
        email: String = randomAlphanumeric() + "@test.com",
        password: String = randomAlphanumeric(12)
    ): String {
        val registrationResponse = ApiCalls.createRegistration(email, password).also { it.status shouldBe HttpStatusCode.Created }
            .body<RegisterResponse>()
        ApiCalls.confirmRegistration(registrationResponse.email)
            .also { it.status shouldBe HttpStatusCode.OK }

        return registrationResponse.email
    }
}
