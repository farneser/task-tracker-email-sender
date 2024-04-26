package dev.farneser.tasktracker.emailsender.services

import com.google.gson.Gson
import dev.farneser.tasktracker.emailsender.dto.StatisticsDto
import org.springframework.stereotype.Service
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class StatisticsMessageService(private val templateEngine: TemplateEngine) : MessageService<StatisticsDto> {
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

    private fun getProjectCaption(name: String): String {
        return "$name statistics"
    }

    override fun parseMessage(message: String): StatisticsDto {
        return Gson().fromJson(message, StatisticsDto::class.java)
    }

    override fun buildHtmlMessage(dto: StatisticsDto): String {
        val context = Context()

        context.setVariable("data", dto)

        val formattedDateTime = getFormattedDate()

        context.setVariable("helloMessage", getHelloMessage(dto.email))
        context.setVariable("caption", getCaption(formattedDateTime))

        return templateEngine.process("scheduled", context)
    }

    override fun buildPlainMessage(dto: StatisticsDto): String {
        val result = StringBuilder()
        val separator = "\r\n"
        val date = getFormattedDate()

        // Hello, {{email}}! Here is your statistic
        //
        // Your statistic for {{date}} {{time}}
        // Status Name           Tasks
        // string                0
        // string                0

        result.append(getHelloMessage(dto.email))
        result.append(separator)
        result.append(separator)
        result.append(getCaption(date))
        result.append(separator)
        result.append(separator)

        for (project in dto.projects) {
            result.append(separator)
            result.append(getProjectCaption(project.projectName))
            result.append(separator)
            result.append(String.format("%-20s  %-20s%s", "Status Name", "Tasks", separator))

            for ((statusName, tasks) in project.statuses) {
                result.append(String.format("%-20s  %-20s%s", statusName, tasks, separator))
            }
        }

        return result.toString()
    }
}