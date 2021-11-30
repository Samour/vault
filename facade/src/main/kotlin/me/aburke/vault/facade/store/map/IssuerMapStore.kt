package me.aburke.vault.facade.store.map

import me.aburke.vault.core.issuer.Issuer
import me.aburke.vault.core.issuer.IssuerStore

class IssuerMapStore : AbstractMapStore<Issuer>(), IssuerStore {

    override fun getAllIssuers(): List<Issuer> = entries.values.toList()

    override fun findIssuerByName(name: String): Issuer? = entries.values.find { it.name == name }

    override fun insert(issuer: Issuer) {
        entries[issuer.id] = issuer
    }
}
