package com.goms.design_system.util

import kotlinx.datetime.LocalDate
import java.time.format.TextStyle
import java.util.Locale

fun String.formatDate(): String {
    val date = LocalDate.parse(this)

    val year = date.year
    val month = date.monthNumber
    val day = date.dayOfMonth

    val dayOfWeek = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN)

    return "${year}년 ${month}월 ${day}일 ($dayOfWeek)"
}