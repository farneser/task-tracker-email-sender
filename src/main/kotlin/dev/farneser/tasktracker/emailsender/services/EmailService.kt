package dev.farneser.tasktracker.emailsender.services

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import org.springframework.util.Assert

@Service
class EmailService(private val javaMailSender: JavaMailSender) {
    @Value("\${spring.mail.username}")
    private lateinit var mailFrom: String

    companion object {
        val log: Logger = LoggerFactory.getLogger(EmailService::class.java)
    }

    fun send(to: String, subject: String, message: String, htmlMessage: String) {
        try {
            val messageHelper = MimeMessageHelper(javaMailSender.createMimeMessage(), "UTF-8")

            Assert.notNull(to, "Email address must not be null")
            Assert.notNull(subject, "Subject must not be null")
            Assert.notNull(message, "Message must not be null")

            messageHelper.setTo(to)
            messageHelper.setSubject(subject)

            if (htmlMessage.isEmpty()) {
                messageHelper.setText(message, false)
            } else {
                messageHelper.setText(message, htmlMessage)
            }

            messageHelper.setFrom(mailFrom)

            javaMailSender.send(messageHelper.mimeMessage)
            log.info("Email sent to $to")
        } catch (e: Exception) {
            log.info("Error sending email to $to", e)
        }
    }
}
