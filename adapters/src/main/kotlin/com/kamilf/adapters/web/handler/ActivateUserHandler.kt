package com.kamilf.adapters.web.handler

import com.kamilf.adapters.web.request.ActivateUserRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import port.Metric
import port.MetricProvider
import usecase.ActivateUserResult
import usecase.ActivateUserUseCase

class ActivateUserHandler(
    private val activateUserUseCase: ActivateUserUseCase,
    private val metricProvider: MetricProvider,
) {
    /** Here is a place when database transaction can be opened
     * or some other logic can be executed before calling use case.
     * Here is good place to handle non-business logic like logging, metrics, etc.
     * */
    suspend fun handle(call: ApplicationCall) {
        // val authenticatedUser = call.jwt().validate()

        val request = call.receive<ActivateUserRequest>()
        val activateUserResult = activateUserUseCase.activateUser(request.email)

        when (activateUserResult) {
            ActivateUserResult.NotAbleToActivateUser -> call.respond(HttpStatusCode.BadRequest)
            ActivateUserResult.UserNotFound -> call.respond(HttpStatusCode.NotFound)
            ActivateUserResult.Success -> {
                metricProvider.sendMetric(Metric.REGISTRATION_CREATED)
                call.respond(HttpStatusCode.OK)
            }
        }
    }
}
