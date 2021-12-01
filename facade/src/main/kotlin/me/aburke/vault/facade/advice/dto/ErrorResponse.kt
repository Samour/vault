package me.aburke.vault.facade.advice.dto

import java.time.Instant

data class ErrorResponse(
    val status: Int,
    val path: String,
    val error: String,
    val timestamp: Instant,
)
