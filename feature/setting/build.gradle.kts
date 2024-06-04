plugins {
    id("goms.android.feature")
    id("goms.android.hilt")
}

android {
    namespace = "com.goms.setting"
}

dependencies {
    implementation(libs.accompanist.permission)
}