package dev.farneser.tasktracker.emailsender.dto

import java.util.*

// if expiresAt is not specified, email confirmation not required and user can authenticate immediately
class ConfirmEmailToken(email: String, val expiresAt: Date? = null, val token: UUID) : EntityMessage(email)