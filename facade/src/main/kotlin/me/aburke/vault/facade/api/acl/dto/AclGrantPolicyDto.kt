package me.aburke.vault.facade.api.acl.dto

data class AclGrantPolicyDto(
    val parameters: List<String>?,
    val applicationMatchCondition: AclMatchConditionDto,
    val scopes: List<String>,
)
