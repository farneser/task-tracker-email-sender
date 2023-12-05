package dev.farneser.tasktracker.emailsender.services

interface MessageService<T> {
    fun parseMessage(message: String): T

    fun buildHtmlMessage(dto: T): String

    fun buildPlainMessage(dto: T): String
}