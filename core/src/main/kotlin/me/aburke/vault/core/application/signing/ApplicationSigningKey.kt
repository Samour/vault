package me.aburke.vault.core.application.signing

import me.aburke.vault.core.encrypted.EncryptedValue
import java.time.Instant

data class ApplicationSigningKey(
    val id: String,
    val applicationId: String,
    val effectiveFrom: Instant,
    val effectiveTo: Instant?,
    val publicKey: ByteArray,
    val privateKey: EncryptedValue,
)
