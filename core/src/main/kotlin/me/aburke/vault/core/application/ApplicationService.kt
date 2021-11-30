package me.aburke.vault.core.application

import me.aburke.vault.core.exceptions.NotFoundException
import org.springframework.stereotype.Service
import java.util.*

@Service
class ApplicationService(private val applicationStore: ApplicationStore) {

    fun listApplications(): List<Application> {
        return applicationStore.getAllApplications()
    }

    fun getApplication(applicationId: String): Application? {
        return applicationStore.findById(applicationId)
    }

    fun createApplication(create: CreateApplication): Application {
        val application = Application(
            id = UUID.randomUUID().toString(),
            tags = create.tags,
            issuerIds = create.issuerIds,
        )
        applicationStore.insert(application)

        return application
    }

    fun updateTags(applicationId: String, tags: List<ApplicationTag>) {
        withApplication(applicationId) { application ->
            val updatedTagNames = tags.map { it.key }
                .toSet()
            val updatedTags = application.tags
                .filter { !updatedTagNames.contains(it.key) } + tags
            applicationStore.setTags(applicationId, updatedTags)
        }
    }

    fun removeTags(applicationId: String, tagKeys: List<String>) {
        withApplication(applicationId) { application ->
            val tags = application.tags.filter { !tagKeys.contains(it.key) }
            applicationStore.setTags(applicationId, tags)
        }
    }

    fun addIssuers(applicationId: String, issuerIds: List<String>) {
        withApplication(applicationId) { application ->
            val updatedIssuers = application.issuerIds.toSet() + issuerIds.toSet()
            applicationStore.setIssuerIds(applicationId, updatedIssuers.toList())
        }
    }

    fun removeIssuers(applicationId: String, issuerIds: List<String>) {
        withApplication(applicationId) { application ->
            val updatedIssuers = application.issuerIds.filter { !issuerIds.contains(it) }
            applicationStore.setIssuerIds(applicationId, updatedIssuers)
        }
    }

    private inline fun withApplication(applicationId: String, action: (application: Application) -> Unit) {
        val application = applicationStore.findById(applicationId) ?: throw NotFoundException("Application not found")
        action(application)
    }
}