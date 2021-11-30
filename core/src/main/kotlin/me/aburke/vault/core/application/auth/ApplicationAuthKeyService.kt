package me.aburke.vault.core.application.auth

import me.aburke.vault.core.encrypted.KeyGenerationService
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class ApplicationAuthKeyService(
    @Value("\${application.key.length}") private val keyLength: Int,
    private val authKeyEncoder: PasswordEncoder,
    private val keyGenerationService: KeyGenerationService,
    private val applicationAuthKeyStore: ApplicationAuthKeyStore,
) {

    fun listAuthKeys(applicationId: String): List<ApplicationAuthKey> =
        applicationAuthKeyStore.findAllByApplicationId(applicationId)

    fun getKey(keyId: String): ApplicationAuthKey? = applicationAuthKeyStore.findById(keyId)

    fun getKeysByFirst8(keyFirst8: String, validTime: Instant): List<ApplicationAuthKey> =
        applicationAuthKeyStore.findAllByFirst8(keyFirst8.take(8), validTime)

    fun createAuthKey(applicationId: String, createKey: CreateAuthKey): DecodedApplicationAuthKey {
        val key = keyGenerationService.createRandomString(keyLength)
        val authKey = ApplicationAuthKey(
            id = UUID.randomUUID().toString(),
            applicationId = applicationId,
            validFrom = createKey.validFrom,
            validTo = createKey.validTo,
            keyFirst8 = key.take(8),
            encodedKey = authKeyEncoder.encode(key),
        )
        applicationAuthKeyStore.insert(authKey)

        return DecodedApplicationAuthKey(
            plaintextKey = key,
            authKey = authKey,
        )
    }

    fun updateKeyValidFrom(keyId: String, validFrom: Instant) {
        applicationAuthKeyStore.updateValidFrom(keyId, validFrom)
    }

    fun updateKeyValidTo(keyId: String, validTo: Instant?) {
        applicationAuthKeyStore.updateValidTo(keyId, validTo)
    }
}
