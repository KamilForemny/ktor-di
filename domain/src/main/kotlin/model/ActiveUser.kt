package model

import java.util.*

data class ActiveUser(
    override val id: UUID,
    override val email: Email,
    override val password: Password,
) : User {
    internal fun block() = BlockedUser(id, email, password)

    internal fun login(password: Password): LoginResult {
        return if (this.password != password) {
            LoginResult.InvalidPassword
        } else LoginResult.Success
    }

    companion object {
        fun create(email: Email, password: Password) = ActiveUser(
            UUID.randomUUID(),
            email,
            password,
        )
    }
}

sealed interface LoginResult {
    data object Success : LoginResult
    data object InvalidPassword : LoginResult
}
