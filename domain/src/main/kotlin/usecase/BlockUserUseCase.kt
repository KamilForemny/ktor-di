package usecase

import model.ActiveUser
import model.Email
import port.UserRepository

class BlockUserUseCase(
    private val userRepository: UserRepository,
) {
    fun blockUser(email: String): BlockUserResult {
        val user = userRepository.find(Email(email)) ?: return BlockUserResult.UserNotFound

        if (user !is ActiveUser) return BlockUserResult.NotAbleToBlockUser

        val blockedUser = user.block()
        userRepository.save(blockedUser)

        return BlockUserResult.Success
    }
}

sealed interface BlockUserResult {
    data object Success : BlockUserResult
    data object UserNotFound : BlockUserResult
    data object NotAbleToBlockUser : BlockUserResult
}
