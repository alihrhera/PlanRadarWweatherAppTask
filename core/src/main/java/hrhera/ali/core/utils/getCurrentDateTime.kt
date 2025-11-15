package hrhera.ali.core.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.formatDateTime(): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd - HH:mm:ss", Locale.getDefault())
    val date = Date(this)
    return dateFormat.format(date)
}

