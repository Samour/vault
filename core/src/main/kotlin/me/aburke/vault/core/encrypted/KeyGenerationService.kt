package me.aburke.vault.core.encrypted

import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.codec.Hex
import org.springframework.stereotype.Service
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.SecureRandom

@Service
class KeyGenerationService(@Value("\${encryption.keypair.size}") private val keypairSize: Int) {

    companion object {
        private const val keyAlgorithm = "RSA"
    }

    fun createRandomString(length: Int): String {
        val bytes = ByteArray(length / 2)
        SecureRandom().nextBytes(bytes)
        return String(Hex.encode(bytes))
    }

    fun createRSAKey(): KeyPair {
        return KeyPairGenerator.getInstance(keyAlgorithm)
            .apply { initialize(keypairSize) }
            .genKeyPair()
    }
}
