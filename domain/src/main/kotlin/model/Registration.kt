package model

data class Registration(
    val email: Email,
    val password: Password,
) {
    internal fun confirm(): ActiveUser {
        return ActiveUser.create(email, password)
    }
}
