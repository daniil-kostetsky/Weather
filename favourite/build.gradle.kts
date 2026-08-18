plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.detekt)
}

android {
    namespace = "com.example.favourite"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

}

dependencies {
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.junit)

    implementation(libs.com.arkivanov.decompose)

    implementation(libs.com.arkivanov.mvikotlin)
    implementation(libs.com.arkivanov.mvikotlin.main)
    implementation(libs.com.arkivanov.mvikotlin.extcoroutines)

    implementation(libs.dagger.core)
    ksp(libs.dagger.compiler)

    implementation(project(":domain"))
    implementation(project(":core"))

    implementation(libs.icons)
    implementation(libs.glide.compose)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.material3)
}
