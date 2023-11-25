package com.example.tasktracker.emailsender

import lombok.Getter

@Getter
class QueueType {
    companion object {
        const val NEW_REGISTER = "new_register"
        const val SCHEDULED = "scheduled"
    }
}
