package dev.farneser.tasktracker.emailsender.listener

import lombok.Getter

@Getter
class QueueType {
    companion object {
        const val NEW_REGISTER = "new_register"
        const val SCHEDULED = "scheduled"
        const val CONFIRM_EMAIL = "confirm_email"
    }
}
