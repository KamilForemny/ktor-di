package com.kamilf.adapters.web.handler

import com.kamilf.adapters.web.request.ConfirmRegistrationRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import port.Metric
import port.MetricProvider
import usecase.ConfirmRegistrationResult
import usecase.ConfirmRegistrationUseCase

class ConfirmRegistrationHandler(
    private val confirmRegistrationUseCase: ConfirmRegistrationUseCase,
    private val metricProvider: MetricProvider,
) {
    suspend fun handle(call: ApplicationCall) {
        val request = call.receive<ConfirmRegistrationRequest>()

        val result = confirmRegistrationUseCase.confirmRegistration(request.email)
        when (result) {
            ConfirmRegistrationResult.RegistrationNotFound -> call.respond(HttpStatusCode.NotFound)
            is ConfirmRegistrationResult.Success -> {
                metricProvider.sendMetric(Metric.USER_CREATED)
                call.respond(HttpStatusCode.OK)
            }
        }
    }
}
