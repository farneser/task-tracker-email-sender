package dev.farneser.tasktracker.emailsender.listener

import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component


@Component
@EnableRabbit
class MessageListener {
    @RabbitListener(queues = [QueueType.NEW_REGISTER])
    fun receiveMessageFromQueue1(message: String) {
        println("Received message from register queue: $message")
    }

    @RabbitListener(queues = [QueueType.SCHEDULED])
    fun receiveMessageFromQueue2(message: String) {
        println("Received message from daily queue: $message")
    }
}
