package dev.farneser.tasktracker.emailsender.listener

import dev.farneser.tasktracker.emailsender.dto.EntityMessage
import dev.farneser.tasktracker.emailsender.services.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component


@Component
@EnableRabbit
class MessageListener(
    private val statisticsService: StatisticsMessageService,
    private val registerService: RegisterMessageService,
    private val confirmEmailService: ConfirmEmailMessageService,
    private val emailService: EmailService
) {
    companion object {
        val log: Logger = LoggerFactory.getLogger(MessageListener::class.java)
    }

    fun <T : EntityMessage> sendMessage(message: String, service: MessageService<T>, subject: String) {
        val dto = service.parseMessage(message)

        log.debug("Parsed message from register queue: {}", dto)

        val plainMessage = service.buildPlainMessage(dto)
        val htmlMessage = service.buildHtmlMessage(dto)

        log.info("Sending email to ${dto.email}")

        emailService.send(dto.email, subject, plainMessage, htmlMessage)
    }

    @RabbitListener(queues = [QueueType.NEW_REGISTER])
    fun receiveMessageFromRegisterQueue(message: String) {
        log.info("Received message from register queue at ${System.currentTimeMillis()} : $message")

        sendMessage(message, registerService, "Task tracker registration")
    }

    @RabbitListener(queues = [QueueType.SCHEDULED])
    fun receiveMessageFromScheduledQueue(message: String) {
        log.info("Received message from scheduled queue at ${System.currentTimeMillis()}: $message")

        sendMessage(message, statisticsService, "Task tracker statistics")
    }

    @RabbitListener(queues = [QueueType.CONFIRM_EMAIL])
    fun receiveMessageFromConfirmEmailQueue(message: String) {
        log.info("Received message from confirm email queue at ${System.currentTimeMillis()} : $message")

        sendMessage(message, confirmEmailService, "Task tracker email confirmation")
    }
}
