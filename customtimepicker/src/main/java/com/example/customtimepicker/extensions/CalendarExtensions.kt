package com.example.customtimepicker.extensions

import java.util.*

var Calendar.year : Int
    get() = get(Calendar.YEAR)
    set(year) {
        set(Calendar.YEAR, year)
    }

var Calendar.month : Int
    get() = get(Calendar.MONTH)
    set(month) {
        set(Calendar.MONTH, month)
    }

var Calendar.dayOfYear : Int
    get() = get(Calendar.DAY_OF_YEAR)
    set(day) {
        set(Calendar.DAY_OF_YEAR, day)
    }

var Calendar.dayOfMonth : Int
    get() = get(Calendar.DAY_OF_MONTH)
    set(day) {
        set(Calendar.DAY_OF_MONTH, day)
    }

var Calendar.weekOfMonth
    get() = get(Calendar.WEEK_OF_MONTH)
    set(week) {
        set(Calendar.WEEK_OF_MONTH, week)
    }

var Calendar.dayOfWeek
    get() = get(Calendar.DAY_OF_WEEK)
    set(day) {
        set(Calendar.DAY_OF_WEEK, day)
    }
