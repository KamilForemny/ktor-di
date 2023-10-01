package com.kamilf.adapters.web.response

import model.Registration

data class RegisterResponse(
    val email: String,
    val password: String,
) {
    companion object {
        fun from(registration: Registration) = RegisterResponse(
            email = registration.email.value,
            password = registration.password.value,
        )
    }
}
