package com.example.detekt.rules

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import org.jetbrains.kotlin.psi.KtCallExpression
import org.jetbrains.kotlin.psi.KtDotQualifiedExpression

class GlobalScopeCoroutineRule(config: Config) : Rule(config) {
    override val issue = Issue(
        id = "GlobalScopeCoroutine",
        severity = Severity.Defect,
        description = "Do not launch coroutines in GlobalScope",
        debt = Debt.FIVE_MINS,
    )

    override fun visitCallExpression(expression: KtCallExpression) {
        super.visitCallExpression(expression)

        val qualifiedExpression = expression.parent as? KtDotQualifiedExpression ?: return
        val receiver = qualifiedExpression.receiverExpression.text
        val functionName = expression.calleeExpression?.text
        if (receiver == "GlobalScope" && functionName in setOf("launch", "async")) {
            report(
                CodeSmell(
                    issue,
                    Entity.from(expression),
                    "Do not use GlobalScope $functionName",
                ),
            )
        }
    }
}
