@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("goms.jvm.library")
}

dependencies {
    implementation(libs.kotlinx.datetime)
}