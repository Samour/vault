package me.aburke.vault.facade.api.issuer.dto

data class IssuerDto(
    val id: String,
    val name: String,
    val publicKey: String,
)
