package hrhera.ali.core.utils

import java.util.Locale

fun Float.toCelsiusFormat(): String {
        val celsius = this - 273.15
        return "${String.format(Locale.ENGLISH, "%.1f", celsius)}°C"
    }