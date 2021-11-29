package me.aburke.vault.core.application

data class Application(
    val id: String,
    val tags: List<ApplicationTag>,
    val issuerIds: List<String>,
)
