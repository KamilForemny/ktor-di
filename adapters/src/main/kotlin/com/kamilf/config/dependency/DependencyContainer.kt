package com.kamilf.config.dependency

import com.kamilf.adapters.database.InMemoryRegistrationRepository
import com.kamilf.adapters.database.InMemoryUserRepository
import com.kamilf.adapters.mailing.FakeEmailSender
import com.kamilf.adapters.metrics.SampleMetricProvider
import com.kamilf.adapters.web.handler.ActivateUserHandler
import com.kamilf.adapters.web.handler.BlockUserHandler
import com.kamilf.adapters.web.handler.ConfirmRegistrationHandler
import com.kamilf.adapters.web.handler.RegisterHandler
import com.typesafe.config.ConfigFactory
import model.PasswordValidationRules
import port.EmailSender
import port.MetricProvider
import port.RegistrationRepository
import port.UserRepository
import usecase.ActivateUserUseCase
import usecase.BlockUserUseCase
import usecase.ConfirmRegistrationUseCase
import usecase.CreateRegistrationUseCase

data class DependencyContainer(
    val appConfig: AppConfig,
) {

    /** Various for adapters, here you can declare adapter implementations for ports declared in domain module */
    private val emailSender: EmailSender = FakeEmailSender(appConfig.emailConfig)
    private val metricProvider: MetricProvider = SampleMetricProvider()

    /** Repositories */
    private val userRepository: UserRepository = InMemoryUserRepository()
    private val registrationRepository: RegistrationRepository = InMemoryRegistrationRepository()

    /** Various for domain */
    private val passwordValidationRules = PasswordValidationRules(
        minLength = appConfig.passwordValidationRulesConfig.minLength,
        maxLength = appConfig.passwordValidationRulesConfig.maxLength,
    )

    /** Use Cases */
    private val createRegistrationUseCase = CreateRegistrationUseCase(registrationRepository, userRepository, emailSender, passwordValidationRules)
    private val activateUserUseCase = ActivateUserUseCase(userRepository)
    private val confirmRegistrationUseCase = ConfirmRegistrationUseCase(registrationRepository, userRepository)
    private val blockUserUseCase = BlockUserUseCase(userRepository)

    /** Handlers */
    val registerHandler = RegisterHandler(createRegistrationUseCase, metricProvider)
    val activateUserHandler = ActivateUserHandler(activateUserUseCase, metricProvider)
    val confirmRegistrationHandler = ConfirmRegistrationHandler(confirmRegistrationUseCase, metricProvider)
    val blockUserHandler = BlockUserHandler(blockUserUseCase, metricProvider)
}

data class AppConfig(
    val ktorConfig: KtorConfig,
    val passwordValidationRulesConfig: PasswordValidationRulesConfig,
    val emailConfig: EmailConfig,
) {
    companion object {
        fun initialize(): AppConfig {
            val config = ConfigFactory.load()

            return AppConfig(
                ktorConfig = KtorConfig(
                    port = config.getInt("ktor.application.port"),
                ),
                passwordValidationRulesConfig = PasswordValidationRulesConfig(
                    minLength = config.getInt("passwordValidationRules.minLength"),
                    maxLength = config.getInt("passwordValidationRules.maxLength"),
                ),
                emailConfig = EmailConfig(
                    serverAddress = config.getString("email.serverAddress"),
                ),
            )
        }
    }

    data class KtorConfig(
        val port: Int,
    )

    data class PasswordValidationRulesConfig(
        val minLength: Int,
        val maxLength: Int,
    )

    data class EmailConfig(
        val serverAddress: String,
    )
}
