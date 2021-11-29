package me.aburke.vault.core.application

interface ApplicationStore {

    fun getAllApplications(): List<Application>

    fun findById(applicationId: String): Application?

    fun insert(application: Application)

    fun setTags(applicationId: String, tags: List<ApplicationTag>)
}