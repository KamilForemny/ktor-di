package com.kamilf.setup

import com.kamilf.startServer
import io.kotest.core.config.AbstractProjectConfig

@Suppress("UNUSED")
object TestRunner : AbstractProjectConfig() {
    override suspend fun beforeProject() {
        EmailServerConfig.configure
        // can start testcontainers here etc

        startServer(wait = false)
    }
}
