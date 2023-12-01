package dev.farneser.tasktracker.emailsender.services

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service

@Service
class EmailService(private val javaMailSender: JavaMailSender) {
    @Value("\${spring.mail.username}")
    private lateinit var mailFrom: String

    companion object {
        val log: Logger = LoggerFactory.getLogger(EmailService::class.java)
    }

    fun send(to: String, subject: String, message: String, isHtml: Boolean = true) {
        try {
            val messageHelper = MimeMessageHelper(javaMailSender.createMimeMessage(), "UTF-8")

            messageHelper.setTo(to)
            messageHelper.setSubject(subject)
            messageHelper.setText(message, isHtml)
            messageHelper.setFrom(mailFrom)

            javaMailSender.send(messageHelper.mimeMessage)
            log.info("Email sent to $to")
        } catch (e: Exception) {
            log.info("Error sending email to $to", e)
        }
    }
}
