package me.aburke.vault.core.application.auth

import me.aburke.vault.core.encrypted.EncryptedValue
import java.time.Instant

data class ApplicationAuthKey(
    val id: String,
    val applicationId: String,
    val issuerId: String,
    val validFrom: Instant,
    val validTo: Instant?,
    val key: EncryptedValue,
)
