package me.aburke.vault.facade.config

import me.aburke.vault.core.application.ApplicationStore
import me.aburke.vault.facade.store.map.ApplicationMapStore
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MapStoreBeans {

    @Bean
    fun applicationStore(): ApplicationStore {
        return ApplicationMapStore()
    }
}