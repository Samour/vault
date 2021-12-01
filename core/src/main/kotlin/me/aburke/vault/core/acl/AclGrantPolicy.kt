package me.aburke.vault.core.acl

data class AclGrantPolicy(
    val parameters: List<String>,
    val applicationMatchCondition: AclMatchCondition,
    val scopes: List<String>,
)
