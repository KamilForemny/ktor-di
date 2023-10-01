package com.kamilf

import com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.kamilf.adapters.web.registerWebEndpoints
import com.kamilf.config.dependency.AppConfig
import com.kamilf.config.dependency.DependencyContainer
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*

fun main() {
    startServer()
}

/**
 * Starts the server with the given configuration.
 * @param appConfig The configuration to use. Can be overrode for testing purposes.
 * @param dependencyContainer The dependency container to use. Can be overrode for testing purposes.
 */
fun startServer(
    wait: Boolean = true,
    appConfig: AppConfig = AppConfig.initialize(),
    dependencyContainer: DependencyContainer = DependencyContainer(appConfig),
) {
    embeddedServer(Netty, port = appConfig.ktorConfig.port, host = "0.0.0.0", module = { module(dependencyContainer) })
        .start(wait = wait)
}

fun Application.module(container: DependencyContainer) {
    routing {
        registerWebEndpoints(
            container.registerHandler,
            container.confirmRegistrationHandler,
            container.activateUserHandler,
            container.blockUserHandler,
        )
    }

    install(ContentNegotiation) {
        jackson {
            jacksonObjectMapper()
                .configure(FAIL_ON_UNKNOWN_PROPERTIES, false)
        }
    }
}
