package com.goms.ui

import com.goms.model.enum.Major

fun Major.toText(): String {
    return when (this) {
        Major.SW_DEVELOP -> "SW개발"
        Major.SMART_IOT -> "IoT"
        Major.AI -> "AI"
    }
}