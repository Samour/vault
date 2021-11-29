package me.aburke.vault.core.encrypted

data class EncryptedValue(
    val value: ByteArray,
    val iv: ByteArray,
)
