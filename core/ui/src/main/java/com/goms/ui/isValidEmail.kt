package com.goms.ui

import com.goms.model.util.Regex

fun isValidEmail(email: String): Boolean {
    return Regex.EMAIL.toRegex().matches(email)
}