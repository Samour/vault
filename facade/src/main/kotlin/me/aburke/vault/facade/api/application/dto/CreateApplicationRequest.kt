package me.aburke.vault.facade.api.application.dto

import me.aburke.vault.core.application.ApplicationTag

data class CreateApplicationRequest(
    val tags: List<ApplicationTag>,
    val issuerIds: List<String>,
)
