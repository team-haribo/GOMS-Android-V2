plugins {
    id("goms.android.feature")
    id("goms.android.hilt")
}

android {
    namespace = "com.goms.main"
}

dependencies {
    implementation(libs.swiperefresh)
    implementation(libs.accompanist.permission)
}