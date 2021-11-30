package me.aburke.vault.facade.converters

import me.aburke.vault.core.issuer.Issuer
import me.aburke.vault.facade.api.issuer.dto.IssuerDto

fun Issuer.toDto(): IssuerDto = IssuerDto(
    id = id,
    name = name
)
