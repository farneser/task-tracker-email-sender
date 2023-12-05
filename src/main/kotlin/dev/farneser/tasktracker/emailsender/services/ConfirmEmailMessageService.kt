package dev.farneser.tasktracker.emailsender.services

import com.google.gson.Gson
import dev.farneser.tasktracker.emailsender.dto.ConfirmEmailToken
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context

@Service
class ConfirmEmailMessageService(private val templateEngine: TemplateEngine) : MessageService<ConfirmEmailToken> {

    @Value("\${application.client.conformation.url")
    val confirmationUrl: String = ""

    override fun parseMessage(message: String): ConfirmEmailToken {
        return Gson().fromJson(message, ConfirmEmailToken::class.java)
    }

    override fun buildHtmlMessage(dto: ConfirmEmailToken): String {
        val context = Context()

        context.setVariable("dto", dto)
        context.setVariable("confirmationUrl", confirmationUrl + dto.token)

        return templateEngine.process("confirm-email", context)
    }

    override fun buildPlainMessage(dto: ConfirmEmailToken): String {
        val result = StringBuilder()

        result.append("Email Confirmation\n\n")
        result.append("Hello, thank you for registering with us. Please click the link below to confirm your email address\n\n")
        result.append(confirmationUrl + dto.token + "\n\n")
        result.append("This link will expire on " + dto.expiresAt + ".\n")
        result.append("If you did not register with us, you can ignore this email.\n")

        return result.toString()
    }
}