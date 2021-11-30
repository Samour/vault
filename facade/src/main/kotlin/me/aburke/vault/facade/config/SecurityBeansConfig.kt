package me.aburke.vault.facade.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.*
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

@Configuration
class SecurityBeansConfig {

    @Bean
    fun encryptionSecretKey(@Value("\${encryption.key}") encryptionKey: String): SecretKey {
        val key = Base64.getDecoder().decode(encryptionKey)
        return SecretKeySpec(
            key,
            0,
            key.size,
            "AES",
        )
    }

    @Bean
    fun authKeyEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}
