package com.kamilf.adapters.web.handler

import com.kamilf.adapters.web.request.RegisterRequest
import com.kamilf.adapters.web.response.RegisterResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import port.EmailSender
import port.Metric
import port.MetricProvider
import usecase.CreateRegistrationResult
import usecase.CreateRegistrationUseCase

class RegisterHandler(
    private val createRegistrationUseCase: CreateRegistrationUseCase,
    private val metricProvider: MetricProvider,
    private val emailSender: EmailSender,
) {
    /** Here is a place when database transaction can be opened
     * or some other logic can be executed before calling use case.
     * Here is good place to handle non-business logic like logging, metrics, etc.
     * */
    suspend fun handle(call: ApplicationCall) {
        val request = call.receive<RegisterRequest>()
        val registrationResult = createRegistrationUseCase.createRegistration(request.email, request.password)

        when (registrationResult) {
            CreateRegistrationResult.EmailAlreadyExists -> call.respond(HttpStatusCode.Conflict)
            CreateRegistrationResult.PasswordInvalid -> call.respond(HttpStatusCode.BadRequest)
            is CreateRegistrationResult.Success -> {
                metricProvider.sendMetric(Metric.REGISTRATION_CREATED)
                emailSender.sendEmail(registrationResult.registration.email)

                val response = RegisterResponse.from(registrationResult.registration)
                call.respond(HttpStatusCode.Created, response)
            }
        }
    }
}
