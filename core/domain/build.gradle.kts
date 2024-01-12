@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("goms.android.core")
    id("goms.android.hilt")
}

android {
    namespace = "com.goms.domain"
}

dependencies {
    implementation(project(":core:data:repository"))
    implementation(project(":core:model"))

    implementation(libs.kotlinx.datetime)
}