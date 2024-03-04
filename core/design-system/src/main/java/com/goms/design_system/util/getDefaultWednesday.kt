package com.goms.design_system.util

import kotlinx.datetime.*
import java.time.DayOfWeek

fun getDefaultWednesday(): Instant {
    val currentDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    val daysUntilWednesday = (currentDate.dayOfWeek.ordinal - DayOfWeek.WEDNESDAY.ordinal + 7) % 7
    return currentDate.minus(daysUntilWednesday.toLong(), DateTimeUnit.DAY).atStartOfDayIn(TimeZone.currentSystemDefault())
}