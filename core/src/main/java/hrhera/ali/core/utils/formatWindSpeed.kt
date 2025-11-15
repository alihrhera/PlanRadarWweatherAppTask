package hrhera.ali.core.utils

import java.util.Locale

fun Float.windSpeedFormat(): String {
    return "${String.format(Locale.ENGLISH, "%.1f", this)} km/h"
}