package hrhera.ali.core.utils

import java.util.Locale

fun String.capitalizeWords(): String =
    this.split(" ").joinToString(" ") { word ->
        word.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
        }
    }