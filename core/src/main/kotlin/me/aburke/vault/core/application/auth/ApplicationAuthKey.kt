package me.aburke.vault.core.application.auth

import java.time.Instant

data class ApplicationAuthKey(
    val id: String,
    val applicationId: String,
    val validFrom: Instant,
    val validTo: Instant?,
    val keyFirst8: String,
    val encodedKey: String,
)
