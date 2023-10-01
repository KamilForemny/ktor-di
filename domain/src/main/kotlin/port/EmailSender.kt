package port

import model.Email

interface EmailSender {
    fun sendEmail(email: Email)
}
