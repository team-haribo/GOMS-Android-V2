package com.goms.design_system.util

import kotlinx.datetime.LocalDate
import java.time.format.TextStyle
import java.util.Locale

fun LocalDate.formatDate(): String {
    val year = this.year
    val month = this.monthNumber
    val day = this.dayOfMonth

    val dayOfWeek = this.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN)

    return "${year}년 ${month}월 ${day}일 ($dayOfWeek)"
}