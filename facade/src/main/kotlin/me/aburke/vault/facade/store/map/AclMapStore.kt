package me.aburke.vault.facade.store.map

import me.aburke.vault.core.acl.Acl
import me.aburke.vault.core.acl.AclGrantPolicy
import me.aburke.vault.core.acl.AclMatchCondition
import me.aburke.vault.core.acl.AclStore
import java.time.Instant

class AclMapStore : AbstractMapStore<Acl>(), AclStore {

    override fun findAll(): List<Acl> = entries.values.toList()

    override fun findById(aclId: String): Acl? = entries[aclId]

    override fun insert(acl: Acl) {
        entries[acl.id] = acl
    }

    override fun updateClientMatchCondition(aclId: String, clientMatchCondition: AclMatchCondition) {
        updateEntry(aclId) { entry ->
            entries[entry.id] = entry.copy(clientMatchCondition = clientMatchCondition)
        }
    }

    override fun updateGrantPolicy(aclId: String, grantPolicy: AclGrantPolicy) {
        updateEntry(aclId) { entry ->
            entries[entry.id] = entry.copy(grantPolicy = grantPolicy)
        }
    }

    override fun updateValidFrom(aclId: String, validFrom: Instant) {
        updateEntry(aclId) { entry ->
            entries[entry.id] = entry.copy(validFrom = validFrom)
        }
    }

    override fun updateValidTo(aclId: String, validTo: Instant?) {
        updateEntry(aclId) { entry ->
            entries[entry.id] = entry.copy(validTo = validTo)
        }
    }

    override fun updateDescription(aclId: String, description: String?) {
        updateEntry(aclId) { entry ->
            entries[entry.id] = entry.copy(description = description)
        }
    }
}
