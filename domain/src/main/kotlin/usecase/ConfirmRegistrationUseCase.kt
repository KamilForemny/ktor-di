package usecase

import model.ActiveUser
import model.Email
import port.RegistrationRepository
import port.UserRepository

class ConfirmRegistrationUseCase(
    private val registrationRepository: RegistrationRepository,
    private val userRepository: UserRepository,
) {
    fun confirmRegistration(email: String): ConfirmRegistrationResult {
        val registration = registrationRepository.find(Email(email))
            ?: return ConfirmRegistrationResult.RegistrationNotFound

        val activeUser = registration.confirm()
        userRepository.save(activeUser)

        return ConfirmRegistrationResult.Success(activeUser)
    }
}

sealed interface ConfirmRegistrationResult {
    data class Success(val user: ActiveUser) : ConfirmRegistrationResult
    data object RegistrationNotFound : ConfirmRegistrationResult
}
