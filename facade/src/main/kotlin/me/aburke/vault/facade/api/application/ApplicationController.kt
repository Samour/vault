package me.aburke.vault.facade.api.application

import me.aburke.vault.core.application.ApplicationService
import me.aburke.vault.core.application.ApplicationTag
import me.aburke.vault.core.application.CreateApplication
import me.aburke.vault.core.application.auth.ApplicationAuthKeyService
import me.aburke.vault.core.application.auth.CreateAuthKey
import me.aburke.vault.core.exceptions.DataError
import me.aburke.vault.core.exceptions.NotFoundException
import me.aburke.vault.facade.api.application.dto.*
import me.aburke.vault.facade.api.dto.ListResponse
import me.aburke.vault.facade.converters.toDto
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/applications")
class ApplicationController(
    private val applicationService: ApplicationService,
    private val applicationAuthKeyService: ApplicationAuthKeyService,
) {

    @GetMapping
    fun listApplications(): ListResponse<ApplicationDto> {
        return applicationService.listApplications()
            .map { it.toDto() }
            .let { ListResponse(items = it) }
    }

    @PostMapping
    fun createApplication(@RequestBody createRequest: CreateApplicationRequest): ApplicationDto {
        return applicationService.createApplication(
            CreateApplication(tags = createRequest.tags)
        ).toDto()
    }

    @GetMapping("/{id}")
    fun getApplication(@PathVariable id: String): ApplicationDto {
        return loadApplication(id)
    }

    @PatchMapping("/{id}/tags")
    fun modifyTags(
        @PathVariable id: String,
        @RequestBody modifyRequest: ModifyApplicationRequest<List<ApplicationTag>, List<String>>,
    ): ApplicationDto {
        if (!modifyRequest.add.isNullOrEmpty()) {
            applicationService.updateTags(id, modifyRequest.add)
        }
        if (!modifyRequest.remove.isNullOrEmpty()) {
            applicationService.removeTags(id, modifyRequest.remove)
        }
        return loadApplication(id)
    }

    @PatchMapping("/{id}/issuers")
    fun modifyIssuers(
        @PathVariable id: String,
        @RequestBody modifyRequest: ModifyApplicationRequest<List<String>, List<String>>,
    ): ApplicationDto {
        if (!modifyRequest.add.isNullOrEmpty()) {
            applicationService.addIssuers(id, modifyRequest.add)
        }
        if (!modifyRequest.remove.isNullOrEmpty()) {
            applicationService.removeIssuers(id, modifyRequest.remove)
        }
        return loadApplication(id)
    }

    @GetMapping("/{id}/keys")
    fun getKeys(@PathVariable id: String): ListResponse<ApplicationAuthKeyDto> {
        return applicationAuthKeyService.listAuthKeys(id)
            .map { it.toDto() }
            .let { ListResponse(it) }
    }

    @PostMapping("/{id}/keys")
    fun createKey(@PathVariable id: String, @RequestBody createRequest: CreateAuthKeyRequest): CreateAuthKeyResponse {
        return applicationAuthKeyService.createAuthKey(
            id,
            CreateAuthKey(
                validTo = createRequest.validTo,
                validFrom = createRequest.validFrom,
            )
        ).toDto()
    }

    @PutMapping("/{id}/keys/{keyId}/validFrom")
    fun updateKeyValidFrom(
        @PathVariable id: String,
        @PathVariable keyId: String,
        @RequestBody updateRequest: UpdateValidFromRequest,
    ): ApplicationAuthKeyDto {
        return updateApplicationAuthKey(id, keyId) {
            applicationAuthKeyService.updateKeyValidFrom(keyId, updateRequest.validFrom)
        }
    }

    @PutMapping("/{id}/keys/{keyId}/validTo")
    fun updateKeyValidTo(
        @PathVariable id: String,
        @PathVariable keyId: String,
        @RequestBody updateRequest: UpdateValidToRequest,
    ): ApplicationAuthKeyDto {
        return updateApplicationAuthKey(id, keyId) {
            applicationAuthKeyService.updateKeyValidTo(keyId, updateRequest.validTo)
        }
    }

    private fun loadApplication(applicationId: String): ApplicationDto =
        applicationService.getApplication(applicationId)?.toDto()
            ?: throw NotFoundException("Application record not found")

    private inline fun updateApplicationAuthKey(
        applicationId: String,
        keyId: String,
        action: () -> Unit,
    ): ApplicationAuthKeyDto {
        val authKey = applicationAuthKeyService.getKey(keyId) ?: throw NotFoundException("AuthKey record not found")
        if (authKey.applicationId != applicationId) {
            throw NotFoundException("AuthKey record not found")
        }
        action()
        return applicationAuthKeyService.getKey(keyId)?.toDto()
            ?: throw DataError("Something went wrong while updating AuthKey(id=${keyId}")
    }
}
