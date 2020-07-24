package com.automattic.freshlypressed.ui.extensions

import java.util.*


/**
 * Created by Ramiz Raja on 22/07/2020.
 */
val Calendar.dayOfMonth: Int
    get() = this.get(Calendar.DAY_OF_MONTH)

fun Date.isDayDifferent(date: Date): Boolean {
    return this.toCalendar().dayOfMonth != date.toCalendar().dayOfMonth
}

fun Date.toCalendar(): Calendar {
    return Calendar.getInstance().apply {
        this.time = this@toCalendar
    }
}