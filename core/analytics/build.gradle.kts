@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("goms.android.core")
    id("goms.android.hilt")
    id("goms.android.compose")
}

android {
    namespace = "com.goms.analytics"
}

dependencies {
    implementation(libs.androidx.compose.runtime)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
}