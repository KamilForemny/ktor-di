package model

import java.util.*

data class BlockedUser(
    override val id: UUID,
    override val email: Email,
    override val password: Password,
) : User {
    internal fun activate() = ActiveUser(id, email, password)
}
