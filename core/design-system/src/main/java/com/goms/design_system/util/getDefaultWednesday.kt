package com.goms.design_system.util

import kotlinx.datetime.*
import java.time.DayOfWeek

fun getDefaultWednesday(): Instant {
    val currentDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    return if (currentDate.dayOfWeek == DayOfWeek.WEDNESDAY) {
        currentDate.atStartOfDayIn(TimeZone.currentSystemDefault())
    } else {
        val lastWednesday = currentDate.minus((currentDate.dayOfWeek.ordinal - DayOfWeek.WEDNESDAY.ordinal).toLong(), DateTimeUnit.DAY)
        lastWednesday.atStartOfDayIn(TimeZone.currentSystemDefault())
    }
}
