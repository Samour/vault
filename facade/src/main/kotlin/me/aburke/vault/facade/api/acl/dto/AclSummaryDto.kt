package me.aburke.vault.facade.api.acl.dto

import java.time.Instant

data class AclSummaryDto(
    val id: String,
    val validFrom: Instant,
    val validTo: Instant?,
    val description: String?,
)
