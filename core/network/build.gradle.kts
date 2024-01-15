@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("goms.android.core")
    id("goms.android.hilt")
}

android {
    namespace = "com.goms.network"
}

dependencies {
    implementation(project(":core:model"))

    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)
    //okhttp
    implementation(libs.okhttp.logging)
    //retrofit
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.retrofit.moshi.converter)
}