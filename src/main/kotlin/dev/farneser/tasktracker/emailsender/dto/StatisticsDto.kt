package dev.farneser.tasktracker.emailsender.dto

class StatisticsDto(email: String) : EntityMessage(email) {
    val projects: ArrayList<ProjectDto> = ArrayList()
}
