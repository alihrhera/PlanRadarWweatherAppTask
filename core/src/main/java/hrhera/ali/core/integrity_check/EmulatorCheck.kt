package hrhera.ali.core.integrity_check

import android.content.Context
import android.os.Build
import android.telephony.TelephonyManager

class EmulatorCheck : IntegrityCheck {
    override fun check(context: Context): Boolean {
        return genymotionCheck() || telephonyCheck(context) || genericCheck()
    }
    private fun genymotionCheck(): Boolean {
        val product = Build.PRODUCT ?: ""
        val manufacturer = Build.MANUFACTURER ?: ""
        val hardware = Build.HARDWARE ?: ""
        val brand = Build.BRAND ?: ""
        val device = Build.DEVICE ?: ""
        return (manufacturer.contains("Genymotion")
                || (brand.startsWith("generic") && device.startsWith("generic"))
                || product == "sdk" || product == "sdk_x86"
                || hardware == "goldfish" || hardware == "ranchu"
                )
    }
    private fun telephonyCheck(context: Context): Boolean {
        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as? TelephonyManager
        tm?.let {
            if (it.phoneType == TelephonyManager.PHONE_TYPE_NONE) {
                return true
            }
        }
        return false
    }
    private fun genericCheck(): Boolean {
        val fingerprint = Build.FINGERPRINT ?: ""
        val model = Build.MODEL ?: ""
        return (fingerprint.startsWith("generic")
                || fingerprint.startsWith("unknown")
                || model.contains("google_sdk")
                || model.contains("Emulator")
                || model.contains("Android SDK built for x86")
                )
    }
}