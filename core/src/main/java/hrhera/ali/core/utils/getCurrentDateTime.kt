package hrhera.ali.core.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.formatDateTime(): String {
    val dateFormat = SimpleDateFormat("dd-MM-yyyy hh:mm a", Locale.getDefault())
    val date = Date(this)
    return dateFormat.format(date)
}

fun Long.formatDate(): String {
    val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    val date = Date(this)
    return dateFormat.format(date)
}

fun Long.formatTime(): String {
    val dateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
    val date = Date(this)
    return dateFormat.format(date)
}

