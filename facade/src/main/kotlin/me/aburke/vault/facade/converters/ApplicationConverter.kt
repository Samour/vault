package me.aburke.vault.facade.converters

import me.aburke.vault.core.application.Application
import me.aburke.vault.facade.api.application.dto.ApplicationDto

fun Application.toDto(): ApplicationDto = ApplicationDto(
    id = this.id,
    tags = this.tags,
    issuerIds = this.issuerIds,
)
