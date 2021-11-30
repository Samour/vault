package me.aburke.vault.core.issuer

import me.aburke.vault.core.encrypted.EncryptedValue

data class IssuerKeyPair(
    val publicKey: ByteArray,
    val privateKey: EncryptedValue,
)
