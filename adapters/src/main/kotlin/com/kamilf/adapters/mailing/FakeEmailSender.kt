package com.kamilf.adapters.mailing

import com.kamilf.config.dependency.AppConfig
import model.Email
import port.EmailSender

class FakeEmailSender(
    private val emailConfig: AppConfig.EmailConfig
) : EmailSender {
    override fun sendEmail(email: Email) {
        println("Sending email to ${email.value}, server address: ${emailConfig.serverAddress}")
    }
}
