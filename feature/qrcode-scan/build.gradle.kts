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
<<<<<<< HEAD

    add("implementation", libs.findLibrary("mlkit").get())

    add("implementation", libs.findLibrary("accompanist.permission").get())
=======
>>>>>>> 9276a7d (:memo: :: Add qrcode module and set dependency)
}
