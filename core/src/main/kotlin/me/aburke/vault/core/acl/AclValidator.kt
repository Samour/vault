package me.aburke.vault.core.acl

import org.springframework.stereotype.Component

@Component
class AclValidator {

    private fun validateSubConditionsForClient(conditions: List<AclMatchCondition>): String? =
        conditions.map { validateClientMatchCondition(it) }
            .find { it != null }

    fun validateClientMatchCondition(matchCondition: AclMatchCondition): String? =
        when (matchCondition) {
            is AclMatchCondition.AclTagParameterMatch -> "clientMatchCondition may not contain AclTagParameterMatch"
            is AclMatchCondition.AclAndCondition -> validateSubConditionsForClient(matchCondition.subConditions)
            is AclMatchCondition.AclOrCondition -> validateSubConditionsForClient(matchCondition.subConditions)
            else -> null
        }

    private fun validateSubConditionsForGrantPolicy(
        conditions: List<AclMatchCondition>,
        parameters: Set<String>,
    ): String? =
        conditions.map { validateGrantPolicyMatchCondition(it, parameters) }
            .find { it != null }

    private fun validateGrantPolicyMatchCondition(matchCondition: AclMatchCondition, parameters: Set<String>): String? =
        when (matchCondition) {
            is AclMatchCondition.AclTagParameterMatch -> {
                if (!parameters.contains(matchCondition.parameterName)) {
                    "applicationMatchCondition contains a parameter not declared by the policy: ${matchCondition.parameterName}"
                } else {
                    null
                }
            }
            is AclMatchCondition.AclAndCondition -> validateSubConditionsForGrantPolicy(
                matchCondition.subConditions,
                parameters,
            )
            is AclMatchCondition.AclOrCondition -> validateSubConditionsForGrantPolicy(
                matchCondition.subConditions,
                parameters,
            )
            else -> null
        }

    fun validateGrantPolicy(grantPolicy: AclGrantPolicy): String? =
        validateGrantPolicyMatchCondition(grantPolicy.applicationMatchCondition, grantPolicy.parameters.toSet())
}
