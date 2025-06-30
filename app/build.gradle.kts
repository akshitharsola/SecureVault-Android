// app/build.gradle.kts
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    // Add kapt for Room
    id("kotlin-kapt")
}

android {
    namespace = "com.securevault"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.securevault"
        minSdk = 24
        targetSdk = 35
        versionCode = 2
        versionName = "1.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            val alias = System.getenv("SIGNING_KEY_ALIAS")?.trim()
            val keyPass = System.getenv("SIGNING_KEY_PASSWORD")?.trim()
            val storePass = System.getenv("SIGNING_STORE_PASSWORD")?.trim()
            
            if (alias != null && keyPass != null && storePass != null) {
                keyAlias = alias
                keyPassword = keyPass
                storeFile = file("keystore.jks")
                storePassword = storePass
                println("Signing config: alias=$alias, keystore=keystore.jks")
            } else {
                println("Missing signing environment variables")
            }
        }
    }
    
    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            
            val hasSigningEnv = System.getenv("SIGNING_KEY_ALIAS") != null &&
                               System.getenv("SIGNING_KEY_PASSWORD") != null &&
                               System.getenv("SIGNING_STORE_PASSWORD") != null
            
            signingConfig = if (hasSigningEnv) {
                println("Using release signing config")
                signingConfigs.getByName("release")
            } else {
                println("No signing config - building unsigned APK")
                null
            }
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
}

dependencies {
    // Core dependencies
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Navigation
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.navigation.runtime.android)
    implementation(libs.androidx.fragment.ktx)

    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)

    // Media
    implementation(libs.androidx.media3.common.ktx)

    // Biometric
    implementation(libs.androidx.biometric)  // Removed biometric-ktx reference

    // Extended Material Icons
    implementation(libs.androidx.material.icons.extended)

    // Gson for JSON serialization/deserialization
    implementation(libs.gson)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    // For file operations
    implementation(libs.androidx.documentfile)

    // For encryption
    implementation(libs.androidx.security.crypto)

    // For file picking
    implementation(libs.androidx.activity.compose.v182)

    implementation(libs.androidx.material3)
}