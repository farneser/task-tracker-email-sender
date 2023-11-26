package dev.farneser.tasktracker.emailsender

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TaskTrackerEmailSenderApplication

fun main(args: Array<String>) {
    runApplication<TaskTrackerEmailSenderApplication>(*args)
}
