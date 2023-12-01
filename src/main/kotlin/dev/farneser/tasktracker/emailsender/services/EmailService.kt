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
        Assert.notNull(to, "Email address must not be null")
        Assert.notNull(subject, "Subject must not be null")
        Assert.notNull(message, "Message must not be null")
        Assert.notNull(htmlMessage, "Html message must not be null")

        try {
            val mimeMessage = javaMailSender.createMimeMessage()
            val messageHelper = MimeMessageHelper(mimeMessage, true, "UTF-8")

            messageHelper.setTo(to)
            messageHelper.setSubject(subject)

            mimeMessage.setHeader("Content-Transfer-Encoding", "quoted-printable")

            messageHelper.setText(message, htmlMessage)
            messageHelper.setFrom(mailFrom)

            javaMailSender.send(mimeMessage)
            log.info("Email sent to $to")
        } catch (e: Exception) {
            log.error("Error sending email to $to", e)
        }
    }
}

