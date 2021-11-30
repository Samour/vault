package me.aburke.vault.facade.api.client

import me.aburke.vault.core.application.LoadApplicationByKey
import me.aburke.vault.core.issuer.IssuerService
import me.aburke.vault.facade.api.dto.ListResponse
import me.aburke.vault.facade.api.issuer.dto.IssuerDto
import me.aburke.vault.facade.converters.toDto
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/client")
class ClientController(
    private val loadApplicationByKey: LoadApplicationByKey,
    private val issuerService: IssuerService,
) {

    @GetMapping("/issuers")
    fun listIssuersForClient(@RequestHeader("Api-Key") apiKey: String): ListResponse<IssuerDto> {
        return loadApplicationByKey.loadApplicationByKey(apiKey)
            .let { issuerService.findIssuersByIds(it.issuerIds) }
            .map { it.toDto() }
            .let { ListResponse(it) }
    }
}
