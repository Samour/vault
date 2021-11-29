package me.aburke.vault.facade

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class VaultApplication

fun main(args: Array<String>) {
    runApplication<VaultApplication>(*args)
}
