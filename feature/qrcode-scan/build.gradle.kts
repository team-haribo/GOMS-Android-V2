import com.goms.goms_android_v2.libs

plugins {
    id("goms.android.feature")
    id("goms.android.hilt")
}

android {
    namespace = "com.goms.qrcode_scan"
}

dependencies {
    add("implementation", libs.findLibrary("camera.core").get())
    add("implementation", libs.findLibrary("camera.view").get())
    add("implementation", libs.findLibrary("camera.camera2").get())
    add("implementation", libs.findLibrary("camera.lifecycle").get())
    add("implementation", libs.findLibrary("camera.extensions").get())
}
