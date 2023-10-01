package com.kamilf.adapters.web

import com.kamilf.adapters.web.handler.ActivateUserHandler
import com.kamilf.adapters.web.handler.BlockUserHandler
import com.kamilf.adapters.web.handler.ConfirmRegistrationHandler
import com.kamilf.adapters.web.handler.RegisterHandler
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Route.registerWebEndpoints(
    registerHandler: RegisterHandler,
    confirmRegistrationHandler: ConfirmRegistrationHandler,
    activateUserHandler: ActivateUserHandler,
    blockUserHandler: BlockUserHandler,
) {
    route("/registrations") {
        post {
            registerHandler.handle(call)
        }

        put("/confirmations") {
            confirmRegistrationHandler.handle(call)
        }
    }

    route("/users") {
        post("/activations") {
            activateUserHandler.handle(call)
        }

        post("/blockings") {
            blockUserHandler.handle(call)
        }
    }

}
