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
fun dailyQueue(): Queue {
    return Queue(QueueType.DAILY, false)
}
