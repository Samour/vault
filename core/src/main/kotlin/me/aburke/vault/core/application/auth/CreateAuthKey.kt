package me.aburke.vault.core.application.auth

import java.time.Instant

data class CreateAuthKey(
    val validFrom: Instant,
    val validTo: Instant?,
)
