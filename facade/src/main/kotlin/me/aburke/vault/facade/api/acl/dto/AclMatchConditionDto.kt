package me.aburke.vault.facade.api.acl.dto

data class AclMatchConditionDto(
    val tag: AclTagMatchDto?,
    val and: List<AclMatchConditionDto>?,
    val or: List<AclMatchConditionDto>?,
)

data class AclTagMatchDto(
    val key: String,
    val values: List<String>?,
    val pattern: String?,
    val parameterName: String?,
)
