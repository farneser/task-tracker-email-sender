package dev.farneser.tasktracker.emailsender.services

import com.google.gson.Gson
import dev.farneser.tasktracker.emailsender.dto.StatisticDto
import org.springframework.stereotype.Service
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context

@Service
class MessageService(private val templateEngine: TemplateEngine) {
    fun parseMessage(message: String): StatisticDto {
        return Gson().fromJson(message, StatisticDto::class.java)
    }

    fun buildMessageBody(dto: StatisticDto): String {
        val context = Context()

        context.setVariable("data", dto)

        return templateEngine.process("scheduled", context)
    }
}