package dev.farneser.tasktracker.emailsender.services

import com.google.gson.Gson
import dev.farneser.tasktracker.emailsender.dto.ConfirmEmailToken
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context

@Service
class RegisterMessageService(private val templateEngine: TemplateEngine) : MessageService<ConfirmEmailToken> {
    @Value("\${application.client.conformation.url}")
    val confirmationUrl: String = ""

    override fun parseMessage(message: String): ConfirmEmailToken {
        return Gson().fromJson(message, ConfirmEmailToken::class.java)
    }

    override fun buildHtmlMessage(dto: ConfirmEmailToken): String {
        val context = Context()

        context.setVariable("dto", dto)
        context.setVariable("confirmationUrl", confirmationUrl + dto.token)

        return templateEngine.process("register", context)
    }

    override fun buildPlainMessage(dto: ConfirmEmailToken): String {
        val result = StringBuilder()

        result.append("Registration Successful!\n\n")
        result.append("Thank you for registering with us. Your email address " + dto.email + " has been successfully registered.\n")

        if (dto.expiresAt != null) {
            result.append("To complete the registration process, please click the following link to confirm your email:\n\n")
            result.append("[confirm email](${confirmationUrl}${dto.token})\n\n")
            result.append("This link will expire on " + dto.expiresAt + ". To get new confirmation email you should try to log in\n")
        }

        return result.toString()
    }
}