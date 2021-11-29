package me.aburke.vault.facade.api.application

import me.aburke.vault.core.application.ApplicationService
import me.aburke.vault.core.application.ApplicationTag
import me.aburke.vault.core.application.CreateApplication
import me.aburke.vault.core.exceptions.NotFoundException
import me.aburke.vault.facade.api.application.dto.ApplicationDto
import me.aburke.vault.facade.api.application.dto.CreateApplicationRequest
import me.aburke.vault.facade.api.application.dto.ListResponse
import me.aburke.vault.facade.api.application.dto.ModifyApplicationRequest
import me.aburke.vault.facade.converters.toDto
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/applications")
class ApplicationController(private val applicationService: ApplicationService) {

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

    private fun loadApplication(applicationId: String): ApplicationDto =
        applicationService.getApplication(applicationId)?.toDto()
            ?: throw NotFoundException("Application record not found")
}