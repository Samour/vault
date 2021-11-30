package me.aburke.vault.facade.converters

import me.aburke.vault.core.issuer.Issuer
import me.aburke.vault.facade.api.issuer.dto.IssuerDto
import java.util.*

fun Issuer.toDto(): IssuerDto = IssuerDto(
    id = id,
    name = name,
    publicKey = Base64.getEncoder().encodeToString(keyPair.publicKey)
)
