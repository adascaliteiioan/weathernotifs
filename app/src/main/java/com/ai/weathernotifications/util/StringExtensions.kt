package com.ai.weathernotifications.util

import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import java.util.*

fun String.toISODateTime(): LocalDate {
    return LocalDate.parse(this, DateTimeFormatter.ISO_DATE_TIME)
}

fun String.toISODate(): LocalDate {
    return LocalDate.parse(this, DateTimeFormatter.ISO_DATE)
}