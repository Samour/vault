package me.aburke.vault.facade.config

import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.context.annotation.ComponentScan

@SpringBootConfiguration
@EnableAutoConfiguration(exclude = [SecurityAutoConfiguration::class])
@ComponentScan("me.aburke.vault")
class AppConfig
