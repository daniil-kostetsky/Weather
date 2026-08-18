import io.gitlab.arturbosch.detekt.extensions.DetektExtension

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlinx.serialization) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.detekt) apply false
}

val detektPluginId = libs.plugins.detekt.get().pluginId

subprojects {
    pluginManager.withPlugin(detektPluginId) {
        extensions.configure<DetektExtension> {
            disableDefaultRuleSets = true
            config.setFrom(rootProject.file("config/detekt/detekt.yml"))
        }
        dependencies {
            add("detektPlugins", project(":detekt-rules"))
        }
    }
}
