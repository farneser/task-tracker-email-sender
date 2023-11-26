package dev.farneser.tasktracker.emailsender.listener

import dev.farneser.tasktracker.emailsender.services.EmailService
import dev.farneser.tasktracker.emailsender.services.MessageService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component


@Component
@EnableRabbit
class MessageListener(private val messageService: MessageService, private val emailService: EmailService) {
    companion object {
        val log: Logger = LoggerFactory.getLogger(MessageListener::class.java)
    }

    @RabbitListener(queues = [QueueType.NEW_REGISTER])
    fun receiveMessageFromRegisterQueue(message: String) {
        log.info("Received message from register queue at ${System.currentTimeMillis()} : $message")
    }

    @RabbitListener(queues = [QueueType.SCHEDULED])
    fun receiveMessageFromScheduledQueue(message: String) {

        log.info("Received message from scheduled queue at ${System.currentTimeMillis()}: $message")

        val dto = messageService.parseMessage(message)

        log.debug("Parsed message from scheduled queue: {}", dto)

        val result = messageService.buildMessageBody(dto)

        log.info("Sending email to ${dto.email}")

        emailService.send(dto.email, "Scheduled email", result)
    }
}
