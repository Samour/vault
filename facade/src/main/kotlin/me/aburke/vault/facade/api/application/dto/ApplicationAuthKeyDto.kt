package me.aburke.vault.facade.api.application.dto

import java.time.Instant

data class ApplicationAuthKeyDto(
    val id: String,
    val applicationId: String,
    val validFrom: Instant,
    val validTo: Instant?,
)
