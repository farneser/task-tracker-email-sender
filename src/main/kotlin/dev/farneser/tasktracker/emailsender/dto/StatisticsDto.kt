package dev.farneser.tasktracker.emailsender.dto

class StatisticsDto(email: String) : EntityMessage(email) {
    val columns: ArrayList<ColumnDto> = ArrayList()
}
