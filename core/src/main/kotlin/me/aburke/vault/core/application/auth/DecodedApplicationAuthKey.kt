package me.aburke.vault.core.application.auth

data class DecodedApplicationAuthKey(
    val authKey: ApplicationAuthKey,
    val plaintextKey: String,
)
