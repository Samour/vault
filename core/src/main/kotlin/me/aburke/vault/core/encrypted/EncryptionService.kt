package me.aburke.vault.core.encrypted

import org.springframework.stereotype.Service
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

@Service
class EncryptionService(private val encryptionSecretKey: SecretKey) {

    companion object {
        private const val algorithm = "AES/CBC/PKCS5Padding"
    }

    fun encryptValue(value: String): EncryptedValue {
        val iv = ByteArray(16)
        SecureRandom().nextBytes(iv)
        val ivSpec = IvParameterSpec(iv)
        val cipher = Cipher.getInstance(algorithm)
        cipher.init(Cipher.ENCRYPT_MODE, encryptionSecretKey, ivSpec)

        return EncryptedValue(
            value = cipher.doFinal(value.toByteArray()),
            iv = iv,
        )
    }

    fun decryptValue(value: EncryptedValue): String {
        val ivSpec = IvParameterSpec(value.iv)
        val cipher = Cipher.getInstance(algorithm)
        cipher.init(Cipher.DECRYPT_MODE, encryptionSecretKey, ivSpec)
        return cipher.doFinal(value.value).decodeToString()
    }
}
