package me.aburke.vault.core.acl

import java.time.Instant

data class CreateAcl(
    val clientMatchCondition: AclMatchCondition,
    val grantPolicy: AclGrantPolicy,
    val validFrom: Instant,
    val validTo: Instant?,
    val description: String?,
)
