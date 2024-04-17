package com.goms.ui

import com.goms.model.util.Regex

fun isValidPassword(password: String): Boolean {
    return Regex.PASSWORD.toRegex().matches(password)
}