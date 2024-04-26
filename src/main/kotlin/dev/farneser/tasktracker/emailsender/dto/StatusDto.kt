package dev.farneser.tasktracker.emailsender.dto

data class StatusDto(val statusName: String, val orderNumber: Long, val tasks: Long) {
}