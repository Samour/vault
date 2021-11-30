package me.aburke.vault.facade.store.map

import me.aburke.vault.core.application.auth.ApplicationAuthKey
import me.aburke.vault.core.application.auth.ApplicationAuthKeyStore
import java.time.Instant

class ApplicationAuthKeyMapStore : AbstractMapStore<ApplicationAuthKey>(), ApplicationAuthKeyStore {

    override fun findById(keyId: String): ApplicationAuthKey? = entries[keyId]

    override fun findAllByApplicationId(applicationId: String): List<ApplicationAuthKey> =
        entries.values
            .filter { it.applicationId == applicationId }

    override fun insert(applicationAuthKey: ApplicationAuthKey) {
        entries[applicationAuthKey.id] = applicationAuthKey
    }

    override fun updateValidFrom(authKeyId: String, validFrom: Instant) {
        updateEntry(authKeyId) { entry ->
            entries[entry.id] = ApplicationAuthKey(
                id = entry.id,
                applicationId = entry.id,
                validFrom = validFrom,
                validTo = entry.validTo,
                encodedKey = entry.encodedKey
            )
        }
    }

    override fun updateValidTo(authKeyId: String, validTo: Instant?) {
        updateEntry(authKeyId) { entry ->
            entries[entry.id] = ApplicationAuthKey(
                id = entry.id,
                applicationId = entry.id,
                validFrom = entry.validFrom,
                validTo = validTo,
                encodedKey = entry.encodedKey
            )
        }
    }
}