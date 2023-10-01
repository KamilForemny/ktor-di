package com.kamilf

import com.kamilf.adapters.web.response.RegisterResponse
import com.kamilf.setup.ApiCalls
import com.kamilf.setup.ApiCalls.createRegistration
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.ktor.client.call.*
import io.ktor.http.*

class RegistrationTest : StringSpec({
    "should crate new registration" {
        // given
        val email = "sample@email.com"

        // when
        val response = createRegistration(email = email)

        // then
        response.status shouldBe HttpStatusCode.Created
        response.body<RegisterResponse>().email shouldBe email
    }

    "should confirm registration and create new user" {
        // given
        val registration = createRegistration().also { it.status shouldBe HttpStatusCode.Created }
            .body<RegisterResponse>()

        // when
        val response = ApiCalls.confirmRegistration(registration.email)

        // then
        response.status shouldBe HttpStatusCode.OK
    }
})

