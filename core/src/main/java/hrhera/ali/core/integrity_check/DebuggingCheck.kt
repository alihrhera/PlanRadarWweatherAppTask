package hrhera.ali.core.integrity_check

import android.content.Context
import android.content.pm.ApplicationInfo
import android.os.Debug

class DebuggingCheck : IntegrityCheck {
    override fun check(context: Context): Boolean {
        return 0 != (context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE)
                || Debug.isDebuggerConnected()
    }
}