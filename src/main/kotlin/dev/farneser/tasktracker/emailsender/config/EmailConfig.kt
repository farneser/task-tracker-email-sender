package dev.farneser.tasktracker.emailsender.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import java.util.*

@Configuration
class EmailConfig {

    @Value("\${spring.mail.host}")
    private val host: String = ""

    @Value("\${spring.mail.port}")
    private val port: Int = 0

    @Value("\${spring.mail.username}")
    private val username: String = ""

    @Value("\${spring.mail.password}")
    private val password: String = ""

    @Value("\${spring.mail.properties.mail.smtp.auth}")
    private val auth: Boolean = false

    @Value("\${spring.mail.properties.mail.smtp.starttls.enable}")
    private val starttls: Boolean = false

    @Value("\${spring.mail.properties.mail.smtp.starttls.required}")
    private val starttlsRequired: Boolean = false

    @Bean
    fun javaMailSender(): JavaMailSender {
        val mailSender = JavaMailSenderImpl()
        mailSender.host = host
        mailSender.port = port
        mailSender.username = username
        mailSender.password = password

        val properties = Properties()
        properties["mail.smtp.auth"] = auth
        properties["mail.smtp.starttls.enable"] = starttls
        properties["mail.smtp.starttls.required"] = starttlsRequired

        mailSender.javaMailProperties = properties

        return mailSender
    }
}
