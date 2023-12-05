package dev.farneser.tasktracker.emailsender.services

import com.google.gson.Gson
import dev.farneser.tasktracker.emailsender.dto.ConfirmEmailToken
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context

@Service
class RegisterMessageService(private val templateEngine: TemplateEngine) : MessageService<ConfirmEmailToken> {
    @Value("\${application.client.conformation.url")
    val confirmationUrl: String = ""

    override fun parseMessage(message: String): ConfirmEmailToken {
        return Gson().fromJson(message, ConfirmEmailToken::class.java)
    }

    override fun buildHtmlMessage(dto: ConfirmEmailToken): String {
        val context = Context()

        context.setVariable("dto", dto)
        context.setVariable("confirmationUrl", confirmationUrl)

        return templateEngine.process("register", context)
    }

    override fun buildPlainMessage(dto: ConfirmEmailToken): String {
        return Gson().toJson(dto)
    }
}