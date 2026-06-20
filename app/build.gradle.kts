plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.partytracker"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.example.partytracker"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
}

dependencies {
    // Standard Android UI libraries
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Preference DataStore for saving data (Assignment Requirement)
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // Coroutines support for safe async saving (Assignment Requirement)
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
}