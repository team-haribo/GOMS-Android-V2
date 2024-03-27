package com.goms.ui

fun isStrongEmail(email: String): Boolean {
    val regex = Regex("^s\\d{5}\$")
    return regex.matches(email)
}