package com.goms.ui

import com.goms.model.util.Regex

fun isValidPassword(password: String): Boolean {
    val regex = Regex(Regex.PASSWORD)
    return regex.matches(password)
}