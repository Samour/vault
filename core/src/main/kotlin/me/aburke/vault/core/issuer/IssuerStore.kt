package me.aburke.vault.core.issuer

interface IssuerStore {

    fun getAllIssuers(): List<Issuer>

    fun findAllByIds(ids: List<String>): List<Issuer>

    fun findIssuerByName(name: String): Issuer?

    fun insert(issuer: Issuer)
}
