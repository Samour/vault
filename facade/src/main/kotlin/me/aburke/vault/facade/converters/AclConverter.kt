package me.aburke.vault.facade.converters

import me.aburke.vault.core.acl.Acl
import me.aburke.vault.core.acl.AclGrantPolicy
import me.aburke.vault.core.acl.AclMatchCondition
import me.aburke.vault.core.acl.CreateAcl
import me.aburke.vault.core.exceptions.InvalidRequestException
import me.aburke.vault.facade.api.acl.dto.*
import me.aburke.vault.facade.converters.AclMatchConditionDtoParser.parse

fun Acl.toSummaryDto(): AclSummaryDto = AclSummaryDto(
    id = id,
    validFrom = validFrom,
    validTo = validTo,
    description = description,
)

fun Acl.toDto(): AclDto = AclDto(
    id = id,
    clientMatchCondition = clientMatchCondition.toDto(),
    grantPolicy = grantPolicy.toDto(),
    validFrom = validFrom,
    validTo = validTo,
    description = description,
)

fun AclMatchCondition.toDto(): AclMatchConditionDto = when (this) {
    is AclMatchCondition.AclTagValuesMatch -> AclMatchConditionDto(
        tag = AclTagMatchDto(
            key = key,
            values = values,
            pattern = null,
            parameterName = null,
        ),
        and = null,
        or = null,
    )
    is AclMatchCondition.AclTagPatternMatch -> AclMatchConditionDto(
        tag = AclTagMatchDto(
            key = key,
            pattern = pattern,
            values = null,
            parameterName = null,
        ),
        and = null,
        or = null,
    )
    is AclMatchCondition.AclTagParameterMatch -> AclMatchConditionDto(
        tag = AclTagMatchDto(
            key = key,
            parameterName = parameterName,
            values = null,
            pattern = null,
        ),
        and = null,
        or = null,
    )
    is AclMatchCondition.AclAndCondition -> AclMatchConditionDto(
        tag = null,
        and = subConditions.map { it.toDto() },
        or = null,
    )
    is AclMatchCondition.AclOrCondition -> AclMatchConditionDto(
        tag = null,
        and = null,
        or = subConditions.map { it.toDto() },
    )
}

fun AclGrantPolicy.toDto(): AclGrantPolicyDto = AclGrantPolicyDto(
    parameters = parameters,
    applicationMatchCondition = applicationMatchCondition.toDto(),
    scopes = scopes,
)

object AclMatchConditionDtoParser {

    private fun parseTag(tagMatchDto: AclTagMatchDto, path: String): AclMatchCondition.AclTagMatch {
        if (tagMatchDto.values != null && tagMatchDto.pattern == null && tagMatchDto.parameterName == null) {
            return AclMatchCondition.AclTagValuesMatch(
                key = tagMatchDto.key,
                values = tagMatchDto.values,
            )
        } else if (tagMatchDto.pattern != null && tagMatchDto.values == null && tagMatchDto.parameterName == null) {
            return AclMatchCondition.AclTagPatternMatch(
                key = tagMatchDto.key,
                pattern = tagMatchDto.pattern,
            )
        } else if (tagMatchDto.parameterName != null && tagMatchDto.values == null && tagMatchDto.pattern == null) {
            return AclMatchCondition.AclTagParameterMatch(
                key = tagMatchDto.key,
                parameterName = tagMatchDto.parameterName,
            )
        } else {
            throw InvalidRequestException("At location '$path': must have exactly 1 of the following fields: values, pattern, parameterName")
        }
    }

    private fun parse(matchConditionDto: AclMatchConditionDto, path: String): AclMatchCondition {
        if (matchConditionDto.tag != null && matchConditionDto.and == null && matchConditionDto.or == null) {
            return parseTag(matchConditionDto.tag, "$path.tag")
        } else if (matchConditionDto.and != null && matchConditionDto.tag == null && matchConditionDto.or == null) {
            return AclMatchCondition.AclAndCondition(
                subConditions = matchConditionDto.and.mapIndexed { i, it -> parse(it, "$path.and[$i]") },
            )
        } else if (matchConditionDto.or != null && matchConditionDto.tag == null && matchConditionDto.and == null) {
            return AclMatchCondition.AclOrCondition(
                subConditions = matchConditionDto.or.mapIndexed { i, it -> parse(it, "$path.or[$i]") },
            )
        } else {
            throw InvalidRequestException("At location '$path': must have exactly 1 of the following fields: tag, and, or")
        }
    }

    fun AclMatchConditionDto.parse(): AclMatchCondition = parse(this, ".")
}

fun CreateAclRequest.parse(): CreateAcl = CreateAcl(
    clientMatchCondition = clientMatchCondition.parse(),
    grantPolicy = AclGrantPolicy(
        parameters = grantPolicy.parameters ?: emptyList(),
        applicationMatchCondition = grantPolicy.applicationMatchCondition.parse(),
        scopes = grantPolicy.scopes,
    ),
    validFrom = validFrom,
    validTo = validTo,
    description = description,
)
