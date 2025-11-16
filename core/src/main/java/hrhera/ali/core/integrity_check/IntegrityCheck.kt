package hrhera.ali.core.integrity_check

import android.content.Context

interface IntegrityCheck {
    fun check(context: Context): Boolean
}
