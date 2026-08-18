package com.example.detekt.rules

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import io.gitlab.arturbosch.detekt.api.CodeSmell
import org.jetbrains.kotlin.psi.KtNamedFunction

class ComposableModifierRule(config: Config) : Rule(config) {
    override val issue = Issue(
        id = "ComposableModifier",
        severity = Severity.Style,
        description = "Composable functions should declare a Modifier parameter.",
        debt = Debt.FIVE_MINS,
    )

    override fun visitNamedFunction(function: KtNamedFunction) {
        super.visitNamedFunction(function)

        val isComposable = function.annotationEntries.any {
            it.shortName?.asString() == "Composable"
        }
        if (!isComposable) return

        val hasModifier = function.valueParameterList?.parameters.orEmpty().any { parameter ->
            parameter.name == "modifier" && parameter.typeReference?.text?.substringAfterLast('.') == "Modifier"
        }
        if (!hasModifier) {
            report(
                CodeSmell(
                    issue,
                    Entity.from(function),
                    "Composable function ${function.name} should declare a Modifier parameter.",
                ),
            )
        }
    }
}
