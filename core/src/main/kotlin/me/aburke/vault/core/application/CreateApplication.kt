package me.aburke.vault.core.application

data class CreateApplication(
    val tags: List<ApplicationTag>,
    val issuerIds: List<String>,
)
