package com.kamilf.setup

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.kamilf.adapters.web.request.ActivateUserRequest
import com.kamilf.adapters.web.request.BlockUserRequest
import com.kamilf.adapters.web.request.ConfirmRegistrationRequest
import com.kamilf.adapters.web.request.RegisterRequest
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*

object ApiCalls {

    private val httpClient by lazy {
        HttpClient(OkHttp) {
            install(ContentNegotiation) {
                jackson {
                    jacksonObjectMapper()
                        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                }
            }
        }
    }

    suspend fun createRegistration(
        email: String = TestData.email,
        password: String = TestData.password
    ): HttpResponse = httpClient.post("http://localhost:8088/registrations") {
        contentType(ContentType.Application.Json)
        setBody(RegisterRequest(email = email, password = password))
    }

    suspend fun activateUser(email: String): HttpResponse = httpClient.post("http://localhost:8088/users/activations") {
        contentType(ContentType.Application.Json)
        setBody(ActivateUserRequest(email = email))
    }

    suspend fun blockUser(email: String): HttpResponse = httpClient.post("http://localhost:8088/users//blockings") {
        contentType(ContentType.Application.Json)
        setBody(BlockUserRequest(email = email))
    }

    suspend fun confirmRegistration(
        email: String,
    ): HttpResponse = httpClient.put("http://localhost:8088/registrations/confirmations") {
        contentType(ContentType.Application.Json)
        setBody(ConfirmRegistrationRequest(email = email))
    }
}

