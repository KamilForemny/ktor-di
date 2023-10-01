val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val ktorClientVersion: String by project

plugins {
    application
    kotlin("jvm") version "1.9.10"
    id("io.ktor.plugin") version "2.3.4"
}

application {
    mainClass.set("com.kamilf.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":domain"))


    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("io.ktor:ktor-server-content-negotiation")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-server-partial-content-jvm:2.3.4")
    implementation("io.ktor:ktor-client-okhttp-jvm:2.3.4")

    /** Ktor Client */
    implementation("io.ktor:ktor-serialization-jackson")
    implementation("io.ktor:ktor-client-core:$ktorClientVersion")
    implementation("io.ktor:ktor-client-okhttp:$ktorClientVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorClientVersion")
    implementation("io.ktor:ktor-client-json:$ktorClientVersion")
    implementation("io.ktor:ktor-client-jackson:$ktorClientVersion")

//    /** Ktor Client */
//    testImplementation("io.ktor:ktor-client-core:$ktorClientVersion")
//    testImplementation("io.ktor:ktor-client-okhttp:$ktorClientVersion")
//    testImplementation("io.ktor:ktor-client-content-negotiation:$ktorClientVersion")
//    testImplementation("io.ktor:ktor-client-json:$ktorClientVersion")
//    testImplementation("io.ktor:ktor-client-jackson:$ktorClientVersion")
}
