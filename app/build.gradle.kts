plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.weather"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.weather"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "WEATHER_API_KEY", "\"${project.findProperty("apiKey") ?: error(
            "You should add apikey into gradle.properties"
        )}\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.foundation)

    implementation(libs.com.arkivanov.decompose)
    implementation(libs.com.arkivanov.jetpack)

    implementation(libs.com.arkivanov.mvikotlin)
    implementation(libs.com.arkivanov.mvikotlin.main)
    implementation(libs.com.arkivanov.mvikotlin.extcoroutines)

    implementation(libs.room.coroutine)
    ksp(libs.room.compiler)

    implementation(libs.dagger.core)
    ksp(libs.dagger.compiler)

    implementation(libs.retrofit.core)
    implementation(libs.retrofit.gson)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.compose.foundation)

    implementation(libs.icons)
    implementation(libs.glide.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}