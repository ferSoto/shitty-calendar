package com.example.customtimepicker.extensions

import java.util.*

val Date.calendar : Calendar
    get() {
        val calendar = Calendar.getInstance()
        calendar.time = this
        return calendar
    }