package com.goms.ui

fun isStrongPassword(password: String): Boolean {
    val regex = Regex("^(?=.[a-zA-Z])(?=.[0-9])(?=.[!@#\$%^&?~])[a-zA-Z0-9!@#\$%^&*?~]{6,15}\$")
    return regex.matches(password)
}