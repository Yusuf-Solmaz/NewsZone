import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.plugin)
}

android {
    namespace = "com.yms.data"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        val secretsPropertiesFile = project.rootProject.file("api.properties")
        val properties = Properties()
        properties.load(secretsPropertiesFile.inputStream())

        val apiKey = properties.getProperty("API_KEY") ?: ""
        val apiKeyGemini = properties.getProperty("GEMINI_API_KEY") ?: ""

        buildConfigField(
            type = "String",
            name = "GEMINI_API_KEY",
            value = apiKeyGemini
        )

        buildConfigField(
            type = "String",
            name = "API_KEY",
            value = apiKey
        )
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(project(":domain"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation (libs.retrofit)
    implementation (libs.okhttp)
    implementation (libs.retrofit.converter)
    implementation(libs.okhttp.logging.interceptor)

    implementation (libs.gson)

    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    implementation(libs.androidx.datastore.preferences)

    implementation(libs.androidx.work.runtime.ktx)

    implementation(libs.hilt.android.work)
    ksp(libs.hilt.androidx.compiler)

    implementation (libs.androidx.paging.runtime.ktx)
    implementation (libs.androidx.paging.compose)

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp (libs.androidx.room.compiler)
    implementation(libs.androidx.room.paging)

    implementation(libs.generativeai)

}
