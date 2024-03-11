import java.io.FileInputStream
import java.util.Properties

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("goms.android.core")
    id("goms.android.compose")
    id("goms.android.lint")
}

android {
    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        buildConfigField("String", "CLIENT_ID",  getApiKey("CLIENT_ID"))
        buildConfigField("String", "REDIRECT_URL",  getApiKey("REDIRECT_URL"))
    }

    namespace = "com.goms.design_system"
}

dependencies {
    implementation(libs.coil.kt.compose)
    implementation(libs.gauth)
    implementation(libs.kotlinx.datetime)
    implementation(libs.androidx.wear.compose)
}

fun getApiKey(propertyKey: String): String {
    val propFile = rootProject.file("./local.properties")
    val properties = Properties()
    properties.load(FileInputStream(propFile))
    return properties.getProperty(propertyKey)
}