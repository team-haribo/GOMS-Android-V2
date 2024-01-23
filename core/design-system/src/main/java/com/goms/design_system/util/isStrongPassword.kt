package com.goms.design_system.util

fun isStrongPassword(password: String): Boolean {
    val regex = Regex("^(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#\$%^&*(),.?\":{}|<>])(.{12,})\$")
    return regex.matches(password)
}