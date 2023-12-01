package dev.farneser.tasktracker.emailsender.services

import com.google.gson.Gson
import dev.farneser.tasktracker.emailsender.dto.StatisticDto
import org.springframework.stereotype.Service
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Service
class MessageService(private val templateEngine: TemplateEngine) {
    private fun getFormattedDate(): String {
        val currentDateTime = LocalDateTime.now()

        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")

        return currentDateTime.format(formatter)
    }

    private fun getHelloMessage(email: String): String {
        return "Hello, $email! Here is your statistic"
    }

    private fun getCaption(date: String): String {
        return "Your statistic for $date"
    }

    fun parseMessage(message: String): StatisticDto {
        return Gson().fromJson(message, StatisticDto::class.java)
    }

    fun buildHtmlMessage(dto: StatisticDto): String {
        val context = Context()

        context.setVariable("data", dto)

        val formattedDateTime = getFormattedDate()

        context.setVariable("helloMessage", getHelloMessage(dto.email))
        context.setVariable("caption", getCaption(formattedDateTime))

        return templateEngine.process("scheduled", context)
    }

    fun buildPlainMessage(dto: StatisticDto): String {
        val result = StringBuilder()
        val separator = "\r\n"
        val date = getFormattedDate()

        // Hello, {{email}}! Here is your statistic
        //
        // Your statistic for {{date}} {{time}}
        // Column Name           Tasks
        // string                0
        // string                0

        result.append(getHelloMessage(dto.email))
        result.append(separator)
        result.append(separator)
        result.append(getCaption(date))
        result.append(separator)

        result.append(String.format("%-20s  %-20s%s", "Column Name", "Tasks", separator))

        for ((columnName, tasks) in dto.columns) {
            result.append(
                String.format(
                    "%-20s  %-20s%s", columnName, tasks, separator
                )
            )
        }
        return result.toString()
    }
}