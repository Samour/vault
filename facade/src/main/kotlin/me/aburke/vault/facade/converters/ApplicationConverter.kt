package me.aburke.vault.facade.converters

import me.aburke.vault.core.application.Application
import me.aburke.vault.core.application.auth.ApplicationAuthKey
import me.aburke.vault.core.application.auth.DecodedApplicationAuthKey
import me.aburke.vault.facade.api.application.dto.ApplicationAuthKeyDto
import me.aburke.vault.facade.api.application.dto.ApplicationDto
import me.aburke.vault.facade.api.application.dto.CreateAuthKeyResponse

fun Application.toDto(): ApplicationDto = ApplicationDto(
    id = id,
    tags = tags,
    issuerIds = issuerIds,
)

fun ApplicationAuthKey.toDto(): ApplicationAuthKeyDto = ApplicationAuthKeyDto(
    id = id,
    applicationId = applicationId,
    validFrom = validFrom,
    validTo = validTo,
)

fun DecodedApplicationAuthKey.toDto(): CreateAuthKeyResponse = CreateAuthKeyResponse(
    id = authKey.id,
    applicationId = authKey.applicationId,
    validFrom = authKey.validFrom,
    validTo = authKey.validTo,
    key = plaintextKey,
)
