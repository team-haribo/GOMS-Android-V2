package com.goms.model.enum

enum class Major(val value: String) {
    SW_DEVELOP("SW"),
    SMART_IOT("IoT"),
    AI("AI")
}

fun Major.toText(): String {
    return when (this) {
        Major.SW_DEVELOP -> "SW개발"
        Major.SMART_IOT -> "IoT"
        Major.AI -> "AI"
    }
}