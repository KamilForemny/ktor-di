package com.kamilf.setup

object EmailServerConfig {
    val configure by lazy {
        System.setProperty("EMAIL_SERVER_ADDRESS", "fake.server.dns")
        Unit
    }
}
