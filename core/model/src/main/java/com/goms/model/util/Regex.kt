package com.goms.model.util

object Regex {
    const val EMAIL = "^s\\d{5}\$"
    const val PASSWORD = "^(?=.*[a-zA-Z])(?=.*[!@#\$%^+=-?<>])(?=.*[0-9]).{6,15}\$"
}