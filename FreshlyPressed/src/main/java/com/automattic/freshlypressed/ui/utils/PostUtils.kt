package com.automattic.freshlypressed.ui.utils

import java.text.DateFormat
import java.util.Date
import java.util.Locale

object PostUtils {
    fun printDate(date: Date): String {
        val dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.UK)
        return dateFormat.format(date)
    }
}
