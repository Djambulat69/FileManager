package com.isaev.filemanager

import android.icu.text.SimpleDateFormat
import java.io.File
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.minutes

fun Double.format(digits: Int) = "%.${digits}f"
    .format(this)
    .split(',')
    .joinToString(".")
    .dropLastWhile { it == '0' || it == '.' }

fun File.readableLength(): String {
    return when (length()) {
        in 0L..1023L -> "${length()} B"
        in 1024L..1_048_575L -> "${(length().toDouble() / 1024).format(2)} kB"
        in 1_048_576L..1_073_741_823L -> "${(length().toDouble() / 1_048_576).format(2)} MB"
        else -> "${(length().toDouble() / 1024).format(2)} GB"
    }
}

fun Long.readableDate(locale: Locale): String {
    val cal = Calendar.getInstance()
    val curYear = cal.get(Calendar.YEAR)

    cal.timeInMillis = this

    val thisYear = cal.get(Calendar.YEAR)

    val format = if (thisYear == curYear) "d MMM" else "d MMM, YYYY"

    return SimpleDateFormat(format, locale).format(cal.time)
}
