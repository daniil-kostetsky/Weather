plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.detekt)
}

android {
    namespace = "com.example.weather"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.example.weather"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "com.example.weather.TestRunner"
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
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.material3)

    implementation(libs.com.arkivanov.decompose)
    implementation(libs.com.arkivanov.jetpack)

    implementation(libs.com.arkivanov.mvikotlin)
    implementation(libs.com.arkivanov.mvikotlin.main)

    implementation(libs.room.coroutine)
    ksp(libs.room.compiler)

    implementation(libs.dagger.core)
    ksp(libs.dagger.compiler)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.compose.foundation)

    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":details"))
    implementation(project(":favourite"))
    implementation(project(":search"))

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
