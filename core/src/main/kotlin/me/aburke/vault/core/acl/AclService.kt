package me.aburke.vault.core.acl

import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class AclService(
    private val aclValidator: AclValidator,
    private val aclStore: AclStore,
) {

    fun listAcls(): List<Acl> = aclStore.findAll()

    fun loadAcl(aclId: String): Acl? = aclStore.findById(aclId)

    fun createAcl(createAcl: CreateAcl): Acl {
        val acl = Acl(
            id = UUID.randomUUID().toString(),
            clientMatchCondition = createAcl.clientMatchCondition,
            grantPolicy = createAcl.grantPolicy,
            validFrom = createAcl.validFrom,
            validTo = createAcl.validTo,
            description = createAcl.description,
        )
        aclStore.insert(acl)

        return acl
    }

    fun updateClientMatchCondition(aclId: String, clientMatchCondition: AclMatchCondition) {
        aclValidator.validateClientMatchCondition(clientMatchCondition)
        aclStore.updateClientMatchCondition(aclId, clientMatchCondition)
    }

    fun updateGrantPolicy(aclId: String, grantPolicy: AclGrantPolicy) {
        aclValidator.validateGrantPolicy(grantPolicy)
        aclStore.updateGrantPolicy(aclId, grantPolicy)
    }

    fun updateValidFrom(aclId: String, validFrom: Instant) {
        aclStore.updateValidFrom(aclId, validFrom)
    }

    fun updateValidTo(aclId: String, validTo: Instant?) {
        aclStore.updateValidTo(aclId, validTo)
    }

    fun updateDescription(aclId: String, description: String?) {
        aclStore.updateDescription(aclId, description)
    }
}
