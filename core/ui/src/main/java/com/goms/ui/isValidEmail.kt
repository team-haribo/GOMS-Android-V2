package com.goms.ui

import com.goms.model.util.Regex

fun isValidEmail(email: String): Boolean {
    val regex = Regex(Regex.EMAIL)
    return regex.matches(email)
}