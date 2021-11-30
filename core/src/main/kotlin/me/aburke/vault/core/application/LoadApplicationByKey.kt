package me.aburke.vault.core.application

import me.aburke.vault.core.application.auth.ApplicationAuthKeyService
import me.aburke.vault.core.exceptions.NotAuthorizedException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class LoadApplicationByKey(
    private val passwordEncoder: PasswordEncoder,
    private val applicationAuthKeyService: ApplicationAuthKeyService,
    private val applicationService: ApplicationService,
) {

    fun loadApplicationByKey(key: String): Application {
        // NOTE: This is super vulnerable to timing attacks
        return applicationAuthKeyService.getKeysByFirst8(key, Instant.now())
            .find { passwordEncoder.matches(key, it.encodedKey) }
            ?.let { applicationService.getApplication(it.applicationId) }
            ?: throw NotAuthorizedException()
    }
}
