package me.aburke.vault.facade.api.application.dto

data class ModifyApplicationRequest<A, R>(
    val add: A?,
    val remove: R?,
)
