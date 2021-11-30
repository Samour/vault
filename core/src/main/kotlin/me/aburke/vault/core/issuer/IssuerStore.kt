package me.aburke.vault.core.issuer

interface IssuerStore {

    fun getAllIssuers(): List<Issuer>

    fun findIssuerByName(name: String): Issuer?

    fun insert(issuer: Issuer)
}
