package dev.farneser.tasktracker.emailsender.dto

import jakarta.validation.constraints.Email

data class StatisticDto(@Email val email: String) {
    val columns: ArrayList<ColumnDto> = ArrayList()
}
