@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("goms.android.core")
    id("goms.android.compose")
    id("goms.android.lint")
}

android {
    namespace = "com.goms.design_system"
}

dependencies {
    implementation(libs.coil.kt.compose)
}