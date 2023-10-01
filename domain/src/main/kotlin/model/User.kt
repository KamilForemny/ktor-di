package model

import java.util.*

interface User {
    val id: UUID
    val email: Email
    val password: Password
}

@JvmInline
value class Password(val value: String) {
    companion object {
        fun crateValidated(password: String, rules: PasswordValidationRules): PasswordValidationResult {
            val valid = rules.checkIfValid(password)
            if (!valid) {
                return PasswordValidationResult.PasswordInvalid
            }
            return PasswordValidationResult.Valid(Password(password))
        }

        sealed interface PasswordValidationResult {
            data class Valid(val password: Password) : PasswordValidationResult
            data object PasswordInvalid : PasswordValidationResult
        }
    }
}

@JvmInline
value class Email(val value: String)


data class PasswordValidationRules(
    val minLength: Int,
    val maxLength: Int,
) {
    fun checkIfValid(password: String): Boolean {
        if (password.length > maxLength) {
            return false
        }

        if (password.length < minLength) {
            return false
        }
        return true
    }
}
