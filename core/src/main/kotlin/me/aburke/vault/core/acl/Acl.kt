package me.aburke.vault.core.acl

import java.time.Instant

data class Acl(
    val id: String,
    val clientMatchCondition: AclMatchCondition,
    val grantPolicy: AclGrantPolicy,
    val validFrom: Instant,
    val validTo: Instant?,
    val description: String?,
)
