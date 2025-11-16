package hrhera.ali.core

import android.content.Context
import android.util.Log
import hrhera.ali.core.integrity_check.DebuggingCheck
import hrhera.ali.core.integrity_check.EmulatorCheck
import hrhera.ali.core.integrity_check.IntegrityCheck
import hrhera.ali.core.integrity_check.IntegrityFacade
import hrhera.ali.core.integrity_check.RootCheck
import hrhera.ali.core.integrity_check.StaticAnalysisToolsCheck

class DeviceIntegrityChecker private constructor(
    private val emulatorChecks: Array<IntegrityCheck>,
    private val rootChecks: Array<IntegrityCheck>
) : IntegrityFacade {
    override fun isEmulator(context: Context): Boolean {

        return emulatorChecks.all {
            it.check(context)
        }
    }

    override fun isRooted(context: Context): Boolean {
        return rootChecks.all { it.check(context) }
    }

    companion object {
        @Volatile
        private var instance: DeviceIntegrityChecker? = null
        private fun getInstance(): DeviceIntegrityChecker {
            return instance ?: synchronized(this) {
                instance ?: DeviceIntegrityChecker(
                    emulatorChecks = arrayOf(EmulatorCheck(), DebuggingCheck()),
                    rootChecks = arrayOf(RootCheck(), StaticAnalysisToolsCheck())
                ).also { instance = it }
            }
        }

        fun check(
            context: Context,
            onEmulator: (Boolean) -> Unit = {},
            onRooted: (Boolean) -> Unit = {}
        ) {
            val checker = getInstance()
            onEmulator(checker.isEmulator(context))
            onRooted(checker.isRooted(context))

        }
    }
}