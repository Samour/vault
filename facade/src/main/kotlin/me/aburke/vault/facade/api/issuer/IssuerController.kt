package me.aburke.vault.facade.api.issuer

import me.aburke.vault.core.issuer.IssuerService
import me.aburke.vault.facade.api.dto.ListResponse
import me.aburke.vault.facade.api.issuer.dto.CreateIssuerRequest
import me.aburke.vault.facade.api.issuer.dto.IssuerDto
import me.aburke.vault.facade.converters.toDto
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/issuers")
class IssuerController(private val issuerService: IssuerService) {

    @GetMapping
    fun listIssuers(): ListResponse<IssuerDto> {
        return issuerService.listIssuers()
            .map { it.toDto() }
            .let { ListResponse(it) }
    }

    @PostMapping
    fun createIssuer(@RequestBody createRequest: CreateIssuerRequest): IssuerDto {
        return issuerService.createIssuer(createRequest.name).toDto()
    }
}
