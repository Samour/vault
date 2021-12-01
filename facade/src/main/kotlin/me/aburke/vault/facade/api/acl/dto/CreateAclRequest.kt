package me.aburke.vault.facade.api.acl.dto

import java.time.Instant

data class CreateAclRequest(
    val clientMatchCondition: AclMatchConditionDto,
    val grantPolicy: AclGrantPolicyDto,
    val validFrom: Instant,
    val validTo: Instant?,
    val description: String?,
)
