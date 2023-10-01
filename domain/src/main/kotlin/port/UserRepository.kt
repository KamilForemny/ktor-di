package port

import model.Email
import model.User

interface UserRepository {
    fun find(email: Email): User?
    fun save(user: User): User
}
