package me.aburke.vault.core.issuer

data class Issuer(
    val id: String,
    val name: String,
    val keyPair: IssuerKeyPair,
)
