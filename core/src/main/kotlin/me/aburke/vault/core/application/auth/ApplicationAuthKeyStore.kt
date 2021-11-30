package me.aburke.vault.core.application.auth

import java.time.Instant

interface ApplicationAuthKeyStore {

    fun findById(keyId: String): ApplicationAuthKey?

    fun findAllByApplicationId(applicationId: String): List<ApplicationAuthKey>

    fun findAllByFirst8(keyFirst8: String, validTime: Instant): List<ApplicationAuthKey>

    fun insert(applicationAuthKey: ApplicationAuthKey)

    fun updateValidFrom(authKeyId: String, validFrom: Instant)

    fun updateValidTo(authKeyId: String, validTo: Instant?)
}
