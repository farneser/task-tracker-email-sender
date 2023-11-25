package com.example.tasktracker.emailsender

import org.springframework.amqp.core.Queue
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMqConfig

@Bean
fun newRegisterQueue(): Queue {
    return Queue(QueueType.NEW_REGISTER)
}

@Bean
fun scheduledQueue(): Queue {
    return Queue(QueueType.SCHEDULED, false)
}
