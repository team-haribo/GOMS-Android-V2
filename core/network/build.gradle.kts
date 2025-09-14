import java.io.FileInputStream
import java.util.Properties

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("goms.android.core")
    id("goms.android.hilt")
}

android {
    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        buildConfigField("String", "BASE_URL", "\"${getApiKey("BASE_URL")}\"")
    }

    namespace = "com.goms.network"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:model"))
    implementation(project(":core:datastore"))

    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.retrofit.moshi.converter)
    implementation(libs.moshi)
    ksp(libs.retrofit.moshi.codegen)
}

fun getApiKey(propertyKey: String): String {
    val propFile = rootProject.file("./local.properties")
    val properties = Properties()
    properties.load(FileInputStream(propFile))
    return properties.getProperty(propertyKey)?.trim('"') ?: ""
}