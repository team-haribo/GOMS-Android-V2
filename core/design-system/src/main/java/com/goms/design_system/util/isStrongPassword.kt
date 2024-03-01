package com.goms.design_system.util

fun isStrongPassword(password: String): Boolean {
    val regex = Regex("^(?=.[a-zA-Z])(?=.[!@#\$%^&?~])[a-zA-Z!@#\$%^&?~]{6,15}\$")
    return regex.matches(password)
}