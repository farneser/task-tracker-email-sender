package dev.farneser.tasktracker.emailsender

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service

@Service
class EmailService(private val javaMailSender: JavaMailSender) {
    companion object {
        val log: Logger = LoggerFactory.getLogger(EmailService::class.java)
    }

    fun send(to: String, subject: String, message: String, isHtml: Boolean = true) {

        try {
            val messageHelper = MimeMessageHelper(javaMailSender.createMimeMessage(), "UTF-8")

            messageHelper.setTo(to)
            messageHelper.setSubject(subject)
            messageHelper.setText(message, isHtml)

            javaMailSender.send(messageHelper.mimeMessage)
        } catch (e: Exception) {
            log.error("Error sending email to $to", e)
        }
    }
}
