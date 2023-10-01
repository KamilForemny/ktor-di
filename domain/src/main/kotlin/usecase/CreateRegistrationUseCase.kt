package usecase

import model.Email
import model.Password
import model.Password.Companion.PasswordValidationResult.PasswordInvalid
import model.Password.Companion.PasswordValidationResult.Valid
import model.PasswordValidationRules
import model.Registration
import port.RegistrationRepository
import port.UserRepository

class CreateRegistrationUseCase(
    private val registrationRepository: RegistrationRepository,
    private val userRepository: UserRepository,
    private val passwordValidationRules: PasswordValidationRules,
) {
    fun createRegistration(email: String, password: String): CreateRegistrationResult {
        val validEmail = Email(email)
        val validPassword = Password.crateValidated(password, passwordValidationRules).let {
            when (it) {
                is Valid -> it.password
                is PasswordInvalid -> return CreateRegistrationResult.PasswordInvalid
            }
        }
        if (registrationRepository.exists(validEmail)) {
            return CreateRegistrationResult.EmailAlreadyExists
        }
        userRepository.find(validEmail)?.let {
            return CreateRegistrationResult.EmailAlreadyExists
        }

        val registration = Registration(validEmail, validPassword)
        registrationRepository.save(registration)

        return CreateRegistrationResult.Success(registration)
    }
}

sealed interface CreateRegistrationResult {
    data class Success(val registration: Registration) : CreateRegistrationResult
    data object EmailAlreadyExists : CreateRegistrationResult
    data object PasswordInvalid : CreateRegistrationResult
}
