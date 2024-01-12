@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("goms.android.core")
    id("goms.android.hilt")
}

android {
    namespace = "com.goms.data"
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
    implementation(libs.retrofit.gson.converter)
}