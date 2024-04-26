package dev.farneser.tasktracker.emailsender.dto

data class ProjectDto(val projectName: String, val statuses: ArrayList<StatusDto>)