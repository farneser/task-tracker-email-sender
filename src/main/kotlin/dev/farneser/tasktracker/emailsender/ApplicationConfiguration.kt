package dev.farneser.tasktracker.emailsender

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl

@Configuration
class ApplicationConfiguration {
    @Bean
    fun javaMailSender(): JavaMailSender {
        return JavaMailSenderImpl()
    }
}