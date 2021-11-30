package me.aburke.vault.facade.config

import me.aburke.vault.core.application.ApplicationStore
import me.aburke.vault.core.application.auth.ApplicationAuthKeyStore
import me.aburke.vault.core.issuer.IssuerStore
import me.aburke.vault.facade.store.map.ApplicationAuthKeyMapStore
import me.aburke.vault.facade.store.map.ApplicationMapStore
import me.aburke.vault.facade.store.map.IssuerMapStore
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MapStoreBeans {

    @Bean
    fun applicationStore(): ApplicationStore = ApplicationMapStore()

    @Bean
    fun applicationAuthKeyStore(): ApplicationAuthKeyStore = ApplicationAuthKeyMapStore()

    @Bean
    fun issuerStore(): IssuerStore = IssuerMapStore()
}
