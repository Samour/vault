package me.aburke.vault.facade.store.map

import me.aburke.vault.core.application.auth.ApplicationAuthKey
import me.aburke.vault.core.application.auth.ApplicationAuthKeyStore
import java.time.Instant

class ApplicationAuthKeyMapStore : AbstractMapStore<ApplicationAuthKey>(), ApplicationAuthKeyStore {

    override fun findById(keyId: String): ApplicationAuthKey? = entries[keyId]

    override fun findAllByApplicationId(applicationId: String): List<ApplicationAuthKey> =
        entries.values
            .filter { it.applicationId == applicationId }

    override fun findAllByFirst8(keyFirst8: String, validTime: Instant): List<ApplicationAuthKey> =
        entries.values
            .filter { it.keyFirst8 == keyFirst8 }
            .filter { !it.validFrom.isAfter(validTime) }
            .filter { it.validTo?.isBefore(validTime) ?: true }

    override fun insert(applicationAuthKey: ApplicationAuthKey) {
        entries[applicationAuthKey.id] = applicationAuthKey
    }

    override fun updateValidFrom(authKeyId: String, validFrom: Instant) {
        updateEntry(authKeyId) { entry ->
            entries[entry.id] = entry.copy(validFrom = validFrom)
        }
    }

    override fun updateValidTo(authKeyId: String, validTo: Instant?) {
        updateEntry(authKeyId) { entry ->
            entries[entry.id] = entry.copy(validTo = validTo)
        }
    }
}
