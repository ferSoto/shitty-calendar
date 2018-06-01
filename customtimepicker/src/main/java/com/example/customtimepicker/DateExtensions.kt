package com.example.customtimepicker

import java.util.*

class DateExtensions {
    companion object {
        val Date.calendar : Calendar
            get() {
                val calendar = Calendar.getInstance()
                calendar.time = this
                return calendar
            }
    }
    private constructor()
}