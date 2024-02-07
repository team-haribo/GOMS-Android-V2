package com.goms.design_system.util

import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.TemporalAdjusters

fun getDefaultWednesday(): Instant {
    val currentDate = LocalDate.now()

    return if (currentDate.dayOfWeek != DayOfWeek.WEDNESDAY) {
        val lastWednesday = currentDate.with(TemporalAdjusters.previous(DayOfWeek.WEDNESDAY))
        lastWednesday.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()
    } else {
        currentDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()
    }
}