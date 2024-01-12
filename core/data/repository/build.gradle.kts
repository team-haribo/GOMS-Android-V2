@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("goms.android.core")
    id("goms.android.hilt")
}

android {
    namespace = "com.goms.repository"
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:data"))

    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)
}