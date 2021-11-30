package me.aburke.vault.facade.api.application.dto

import java.time.Instant

data class CreateAuthKeyRequest(
    val validFrom: Instant,
    val validTo: Instant?,
)
