plugins {
    id("goms.android.application")
    id("goms.android.hilt")
}

android {
    namespace = "com.goms.goms_android_v2"

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/DEPENDENCIES"
        }
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:design-system"))
    implementation(project(":core:data"))
    implementation(project(":core:model"))
    implementation(project(":feature:login"))
    implementation(project(":feature:sign-up"))
    implementation(project(":feature:main"))
    implementation(project(":feature:qrcode-scan"))

    implementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext)
    implementation(libs.androidx.core.splashscreen)
}