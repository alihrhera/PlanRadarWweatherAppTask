package hrhera.ali.core.integrity_check

import android.content.Context
import java.io.File

class StaticAnalysisToolsCheck : IntegrityCheck {
    override fun check(context: Context): Boolean {
        val suspiciousProcesses = listOf("frida-server", "xposed")
        val processes = File("/proc").listFiles()?.map { it.name } ?: listOf()
        return suspiciousProcesses.any { processes.contains(it) }

    }
}