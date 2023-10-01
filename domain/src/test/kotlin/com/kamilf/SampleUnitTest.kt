package com.kamilf

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.beInstanceOf
import model.PasswordValidationRules
import usecase.CreateRegistrationResult
import usecase.CreateRegistrationUseCase

class SampleUnitTest : StringSpec({
    val setup = UseCaseTestSetup() // to be decided if create a separate class for each test case or one for all

    "sample Unit test" {
        "sample".length shouldBe 6
    }

    "sample use case test if needed" {
        // given
        val validationRules = PasswordValidationRules(minLength = 5, maxLength = 10)
        val systemUnderTest = CreateRegistrationUseCase(setup.registrationRepository, setup.userRepository, validationRules)

        // when
        val result = systemUnderTest.createRegistration("sample@email.com", "password")

        // then
        result should beInstanceOf<CreateRegistrationResult.Success>()
    }
})
