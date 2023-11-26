package dev.farneser.tasktracker.emailsender.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl

@Configuration
class ApplicationConfig {
    @Bean
    fun javaMailSender(): JavaMailSender {
        return JavaMailSenderImpl()
    }
}