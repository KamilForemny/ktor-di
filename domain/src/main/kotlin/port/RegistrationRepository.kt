package port

import model.Email
import model.Registration

interface RegistrationRepository {
    fun save(registration: Registration): Registration
    fun find(email: Email): Registration?
    fun exists(email: Email): Boolean
}
