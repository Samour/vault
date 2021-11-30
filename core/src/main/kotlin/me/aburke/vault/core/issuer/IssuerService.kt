package me.aburke.vault.core.issuer

import me.aburke.vault.core.encrypted.EncryptionService
import me.aburke.vault.core.encrypted.KeyGenerationService
import me.aburke.vault.core.exceptions.InvalidRequestException
import org.springframework.stereotype.Service
import java.util.*
import javax.annotation.PostConstruct

@Service
class IssuerService(
    private val keyGenerationService: KeyGenerationService,
    private val encryptionService: EncryptionService,
    private val issuerStore: IssuerStore,
) {

    companion object {
        private const val defaultIssuerName = "default"
    }

    @PostConstruct
    fun createDefaultIssuer() {
        if (listIssuers().isEmpty()) {
            createIssuer(defaultIssuerName)
        }
    }

    fun listIssuers(): List<Issuer> = issuerStore.getAllIssuers()

    fun findIssuersByIds(ids: List<String>): List<Issuer> = issuerStore.findAllByIds(ids)

    fun getIssuerByName(name: String): Issuer? = issuerStore.findIssuerByName(name)

    fun createIssuer(name: String): Issuer {
        if (getIssuerByName(name) != null) {
            throw InvalidRequestException("Issuer with name '${name}' already exists")
        }

        val keyPair = keyGenerationService.createRSAKey()
        val issuerKeyPair = IssuerKeyPair(
            publicKey = keyPair.public.encoded,
            privateKey = encryptionService.encryptValue(keyPair.private.encoded)
        )
        val issuer = Issuer(
            id = UUID.randomUUID().toString(),
            name = name,
            keyPair = issuerKeyPair
        )
        issuerStore.insert(issuer)

        return issuer
    }
}
