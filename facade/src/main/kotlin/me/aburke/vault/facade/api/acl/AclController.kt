package me.aburke.vault.facade.api.acl

import me.aburke.vault.core.acl.Acl
import me.aburke.vault.core.acl.AclGrantPolicy
import me.aburke.vault.core.acl.AclService
import me.aburke.vault.core.exceptions.DataError
import me.aburke.vault.core.exceptions.NotFoundException
import me.aburke.vault.facade.api.acl.dto.*
import me.aburke.vault.facade.api.dto.ListResponse
import me.aburke.vault.facade.converters.AclMatchConditionDtoParser.parse
import me.aburke.vault.facade.converters.parse
import me.aburke.vault.facade.converters.toDto
import me.aburke.vault.facade.converters.toSummaryDto
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/acls")
class AclController(private val aclService: AclService) {

    @GetMapping
    fun listAcls(): ListResponse<AclSummaryDto> {
        return aclService.listAcls()
            .map { it.toSummaryDto() }
            .let { ListResponse(it) }
    }

    @PostMapping
    fun createAcl(@RequestBody createRequest: CreateAclRequest): AclDto {
        return aclService.createAcl(createRequest.parse()).toDto()
    }

    @GetMapping("/{id}")
    fun getAcl(@PathVariable id: String): AclDto {
        return loadAcl(id).toDto()
    }

    @PutMapping("/{id}/clientMatchCondition")
    fun updateClientMatchCondition(
        @PathVariable id: String,
        @RequestBody clientMatchCondition: AclMatchConditionDto,
    ): AclDto = updateAcl(id) {
        aclService.updateClientMatchCondition(id, clientMatchCondition.parse())
    }

    @PutMapping("/{id}/grantPolicy")
    fun updateGrantPolicy(@PathVariable id: String, @RequestBody grantPolicy: AclGrantPolicyDto): AclDto =
        updateAcl(id) {
            aclService.updateGrantPolicy(
                id,
                AclGrantPolicy(
                    parameters = grantPolicy.parameters ?: emptyList(),
                    applicationMatchCondition = grantPolicy.applicationMatchCondition.parse(),
                    scopes = grantPolicy.scopes,
                )
            )
        }

    @PutMapping("/{id}/validFrom")
    fun updateValidFrom(@PathVariable id: String, @RequestBody updateValidFrom: UpdateAclValidFromRequest): AclDto =
        updateAcl(id) {
            aclService.updateValidFrom(id, updateValidFrom.validFrom)
        }

    @PutMapping("/{id}/validTo")
    fun updateValidTo(@PathVariable id: String, @RequestBody updateValidTo: UpdateAclValidToRequest): AclDto =
        updateAcl(id) {
            aclService.updateValidTo(id, updateValidTo.validTo)
        }

    @PutMapping("/{id}/description")
    fun updateDescription(
        @PathVariable id: String,
        @RequestBody updateDescription: UpdateAclDescriptionRequest,
    ): AclDto = updateAcl(id) {
        aclService.updateDescription(id, updateDescription.description)
    }

    private fun loadAcl(aclId: String): Acl = aclService.loadAcl(aclId) ?: throw NotFoundException("Acl not found")

    private fun updateAcl(aclId: String, action: () -> Unit): AclDto {
        loadAcl(aclId)
        action()
        return aclService.loadAcl(aclId)?.toDto() ?: throw DataError("Data consistency issue updating Acl(id=${aclId})")
    }
}
