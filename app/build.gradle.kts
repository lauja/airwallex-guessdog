@file:Suppress("UnstableApiUsage")

import app.cash.paparazzi.gradle.PaparazziPlugin

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.paparazzi)
}

android {
    namespace = "com.jasonlau.guessdog"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.jasonlau.guessdog"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            isDebuggable = true
        }

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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    testOptions {
        unitTests {
            all {
                // filter out paparazzi tests when running unit tests
                val isPaparazzi = gradle.startParameter.taskNames.any { taskName -> taskName.contains("Paparazzi") }
                if (!isPaparazzi) {
                    it.exclude("**/*UiTest.class")
                }
            }
        }
    }
}

// filter out unit tests when running paparazzi tests
tasks.withType<PaparazziPlugin.PaparazziTask> {
    val isPaparazzi = gradle.startParameter.taskNames.any { it.contains("Paparazzi") }
    if (isPaparazzi) {
        setTestNameIncludePatterns(listOf("*UiTest"))
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Hilt
    implementation(libs.hilt.android.core)
    implementation(libs.androidx.hilt.navigation.compose)
    ksp(libs.hilt.compiler)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.convertor.gson)

    // Coil
    implementation(libs.coil.compose)

    implementation(libs.paparazzi) {
        exclude(group = "org.bouncycastle", module = "bcpkix-jdk18on")
            .because("The version of bcpkix-jdk15on brought-in by paparazzi causes `TrustAllX509TrustManager` lint error.")
    }

    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.testParameterInjector)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}