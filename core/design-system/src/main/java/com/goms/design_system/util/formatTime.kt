package com.goms.design_system.util

fun String.formatTime(): String {
    if (this.length >= 5) {
        return this.substring(0, 5)
    }
    return this
}