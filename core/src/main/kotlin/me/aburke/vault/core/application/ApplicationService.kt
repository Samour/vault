package me.aburke.vault.core.application

import me.aburke.vault.core.exceptions.InvalidRequestException
import me.aburke.vault.core.exceptions.NotFoundException
import org.springframework.stereotype.Service
import java.util.*

@Service
class ApplicationService(val applicationStore: ApplicationStore) {

    fun listApplications(): List<Application> {
        return applicationStore.getAllApplications()
    }

    fun getApplication(applicationId: String): Application? {
        return applicationStore.findById(applicationId)
    }

    fun createApplication(create: CreateApplication): Application {
        if (create.tags.find { it.key == TagNames.name } == null) {
            throw InvalidRequestException("\"${TagNames.name}\" is a required tag");
        }

        val application = Application(
            id = UUID.randomUUID().toString(),
            tags = create.tags,
            issuer = false,
            isserName = null,
        )
        applicationStore.insert(application)

        return application
    }

    fun updateTags(applicationId: String, tags: List<ApplicationTag>) {
        val application = applicationStore.findById(applicationId) ?: throw NotFoundException("Application not found")
        val updatedTagNames = tags.map { it.key }
            .toSet()
        val updatedTags = application.tags
            .filter { !updatedTagNames.contains(it.key) } + tags
        applicationStore.setTags(applicationId, updatedTags)
    }

    fun removeTags(applicationId: String, tagKeys: List<String>) {
        if (tagKeys.find { it == TagNames.name } != null) {
            throw InvalidRequestException("Tag \"${TagNames.name}\" may not be removed")
        }
        val application = applicationStore.findById(applicationId) ?: throw NotFoundException("Application not found")
        val tags = application.tags.filter { !tagKeys.contains(it.key) }
        applicationStore.setTags(applicationId, tags)
    }
}