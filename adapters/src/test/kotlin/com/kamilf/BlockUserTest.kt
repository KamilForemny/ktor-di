package com.kamilf

import com.kamilf.setup.ApiCalls
import com.kamilf.setup.TestData
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.ktor.http.*

class BlockUserTest : StringSpec({
    "should block User" {
        // given
        val activeUserEmail = TestData.givenActiveUser()

        // when
        val response = ApiCalls.blockUser(activeUserEmail)

        // then
        response.status shouldBe HttpStatusCode.OK
    }
})
