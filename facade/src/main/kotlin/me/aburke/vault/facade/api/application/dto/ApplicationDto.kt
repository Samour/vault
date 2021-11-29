package me.aburke.vault.facade.api.application.dto

import me.aburke.vault.core.application.ApplicationTag

data class ApplicationDto(
    val id: String,
    val tags: List<ApplicationTag>,
    val issuerIds: List<String>,
)
