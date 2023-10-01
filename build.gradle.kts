import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val logbackVersion: String by project
val kotestVersion: String by project

plugins {
    base
    kotlin("jvm") version "1.9.10" apply false
}

subprojects {
    apply { plugin("org.jetbrains.kotlin.jvm") }

    group = "com.kamilf"
    version = "0.0.1"

    repositories {
        mavenCentral()
    }

    val implementation by configurations
    val testImplementation by configurations

    dependencies {
        /** Kotlin */
        implementation(kotlin("stdlib"))

        implementation("ch.qos.logback:logback-classic:$logbackVersion")

        /** Kotest */
        testImplementation("io.kotest:kotest-runner-junit5-jvm:$kotestVersion")
        testImplementation("io.kotest:kotest-assertions-core-jvm:$kotestVersion")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}
