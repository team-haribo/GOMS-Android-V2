@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("goms.android.core")
    id("goms.android.hilt")
}

android {
    namespace = "com.goms.common"
}

dependencies {
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)
}