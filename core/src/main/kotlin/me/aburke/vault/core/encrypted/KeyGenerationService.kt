package me.aburke.vault.core.encrypted

import org.springframework.security.crypto.codec.Hex
import org.springframework.stereotype.Service
import java.security.SecureRandom

@Service
class KeyGenerationService {

    fun createRandomString(length: Int): String {
        val bytes = ByteArray(length / 2)
        SecureRandom().nextBytes(bytes)
        return String(Hex.encode(bytes))
    }
}
