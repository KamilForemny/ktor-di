package com.kamilf.adapters.web.handler

import com.kamilf.adapters.web.request.BlockUserRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import port.Metric
import port.MetricProvider
import usecase.BlockUserResult
import usecase.BlockUserUseCase

class BlockUserHandler(
    private val blockUserUseCase: BlockUserUseCase,
    private val metricProvider: MetricProvider,
) {
    suspend fun handle(call: ApplicationCall) {
        val request = call.receive<BlockUserRequest>()

        val blockUserResult = blockUserUseCase.blockUser(request.email)
        when (blockUserResult) {
            BlockUserResult.NotAbleToBlockUser -> call.respond(HttpStatusCode.BadRequest)
            BlockUserResult.UserNotFound -> call.respond(HttpStatusCode.NotFound)
            BlockUserResult.Success -> {
                metricProvider.sendMetric(Metric.USER_BLOCKED)
                call.respond(HttpStatusCode.OK)
            }
        }
    }
}
