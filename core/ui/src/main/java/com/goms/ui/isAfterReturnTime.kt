package com.goms.ui

import kotlinx.datetime.LocalTime
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun isAfterReturnTime(returnTime: LocalTime = LocalTime(19, 25)): Boolean {
    val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time

    return now > returnTime
}
