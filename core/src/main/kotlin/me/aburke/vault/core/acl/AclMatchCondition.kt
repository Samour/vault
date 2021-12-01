package me.aburke.vault.core.acl

sealed class AclMatchCondition {
    sealed class AclTagMatch : AclMatchCondition() {
        abstract val key: String
    }

    data class AclTagValuesMatch(override val key: String, val values: List<String>) : AclTagMatch()

    data class AclTagPatternMatch(override val key: String, val pattern: String) : AclTagMatch()

    data class AclTagParameterMatch(override val key: String, val parameterName: String) : AclTagMatch()

    data class AclAndCondition(val subConditions: List<AclMatchCondition>) : AclMatchCondition()

    data class AclOrCondition(val subConditions: List<AclMatchCondition>) : AclMatchCondition()
}
