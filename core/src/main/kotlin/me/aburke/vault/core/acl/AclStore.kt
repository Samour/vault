package me.aburke.vault.core.acl

import java.time.Instant

interface AclStore {

    fun findAll(): List<Acl>

    fun findById(aclId: String): Acl?

    fun insert(acl: Acl)

    fun updateClientMatchCondition(aclId: String, clientMatchCondition: AclMatchCondition)

    fun updateGrantPolicy(aclId: String, grantPolicy: AclGrantPolicy)

    fun updateValidFrom(aclId: String, validFrom: Instant)

    fun updateValidTo(aclId: String, validTo: Instant?)

    fun updateDescription(aclId: String, description: String?)
}
