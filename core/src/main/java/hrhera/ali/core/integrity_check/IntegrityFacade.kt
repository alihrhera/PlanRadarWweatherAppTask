package hrhera.ali.core.integrity_check

import android.content.Context

interface IntegrityFacade {
    fun isEmulator(context: Context): Boolean
    fun isRooted(context: Context): Boolean
}