package com.example.customtimepicker.extensions

import java.util.*

val Date.calendar : Calendar
    get() {
        val calendar = Calendar.getInstance()
        calendar.time = this
        return calendar
    }

val Date.dayOfWeek : Int
    get() = calendar.dayOfWeek

val Date.isWeekend : Boolean
    get() {
        val day = dayOfWeek
        return (day == Calendar.SATURDAY || day == Calendar.SUNDAY)
    }