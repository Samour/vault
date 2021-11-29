package me.aburke.vault.facade.store.map

import me.aburke.vault.core.application.Application
import me.aburke.vault.core.application.ApplicationStore
import me.aburke.vault.core.application.ApplicationTag

class ApplicationMapStore : AbstractMapStore<Application>(), ApplicationStore {

    override fun getAllApplications(): List<Application> = entries.values.toList()

    override fun findById(applicationId: String): Application? = entries[applicationId]

    override fun insert(application: Application) {
        entries[application.id] = application
    }

    override fun setTags(applicationId: String, tags: List<ApplicationTag>) {
        updateEntry(applicationId) { entry ->
            entries[applicationId] = Application(
                id = entry.id,
                tags = tags,
                issuerIds = entry.issuerIds
            )
        }
    }

    override fun setIssuerIds(applicationId: String, issuerIds: List<String>) {
        updateEntry(applicationId) { entry ->
            entries[applicationId] = Application(
                id = entry.id,
                tags = entry.tags,
                issuerIds = issuerIds
            )
        }
    }
}
