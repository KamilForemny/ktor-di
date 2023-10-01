package usecase

import model.BlockedUser
import model.Email
import port.UserRepository

class ActivateUserUseCase(
    private val userRepository: UserRepository,
) {
    fun activateUser(email: String): ActivateUserResult {
        val user = userRepository.find(Email(email))
            ?: return ActivateUserResult.UserNotFound

        if (user !is BlockedUser) {
            return ActivateUserResult.NotAbleToActivateUser
        }

        userRepository.save(user.activate())
        return ActivateUserResult.Success
    }
}

sealed interface ActivateUserResult {
    data object Success : ActivateUserResult
    data object UserNotFound : ActivateUserResult
    data object NotAbleToActivateUser : ActivateUserResult
}
