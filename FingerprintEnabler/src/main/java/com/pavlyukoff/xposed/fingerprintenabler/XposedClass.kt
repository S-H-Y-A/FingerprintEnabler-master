package com.pavlyukoff.xposed.fingerprintenabler

import android.os.Build
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam

class XposedClass : IXposedHookLoadPackage {

    @Throws(Throwable::class)
    override fun handleLoadPackage(lpparam: LoadPackageParam) {
        if (lpparam.packageName != "com.android.systemui") return

        HookHelper(lpparam.classLoader).apply {

            // 00.00 -------------------------------------------------------------------------------
            //this MUST disable request PIN at all - DANGEROUS
/*            hook(
                Build.VERSION_CODES.M .. Build.VERSION_CODES.CUR_DEVELOPMENT,
                "com.android.keyguard.KeyguardUpdateMonitor",
                "getUserCanSkipBouncer",
                true,
                Int::class.javaPrimitiveType
            )*/

            // 00.01 -------------------------------------------------------------------------------
            hook(
                Build.VERSION_CODES.M .. Build.VERSION_CODES.P,
                KeyguardUpdateMonitor_clazz,
                "isUnlockingWithFingerprintAllowed",
                true
            )

            // 00.01 - new -------------------------------------------------------------------------
            hook(
                Build.VERSION_CODES.Q .. Build.VERSION_CODES.Q,
                KeyguardUpdateMonitor_clazz,
                "isUnlockingWithBiometricAllowed",
                true
            )

            hook(
                Build.VERSION_CODES.R .. Build.VERSION_CODES.CUR_DEVELOPMENT,
                KeyguardUpdateMonitor_clazz,
                "isUnlockingWithBiometricAllowed",
                true,
                Boolean::class.javaPrimitiveType
            )

            // 00.02 -------------------------------------------------------------------------------
            hook(
                Build.VERSION_CODES.M .. Build.VERSION_CODES.CUR_DEVELOPMENT,
                KeyguardUpdateMonitor_clazz,
                "isUnlockWithFingerprintPossible",
                true,
                Int::class.javaPrimitiveType
            )

            // 00.03 -------------------------------------------------------------------------------
            hook(
                Build.VERSION_CODES.M .. Build.VERSION_CODES.CUR_DEVELOPMENT,
                KeyguardUpdateMonitor_clazz,
                "isFingerprintDisabled",
                false,
                Int::class.javaPrimitiveType
            )

            // 01.00 -------------------------------------------------------------------------------
            hook(
                Build.VERSION_CODES.M .. Build.VERSION_CODES.P,
                KeyguardUpdateMonitor_StrongAuthTracker_clazz,
                "isUnlockingWithFingerprintAllowed",
                true
            )

            // 01.00 - new -------------------------------------------------------------------------------
            hook(
                Build.VERSION_CODES.Q .. Build.VERSION_CODES.Q,
                KeyguardUpdateMonitor_StrongAuthTracker_clazz,
                "isUnlockingWithBiometricAllowed",
                true
            )

            hook(
                Build.VERSION_CODES.R .. Build.VERSION_CODES.CUR_DEVELOPMENT,
                KeyguardUpdateMonitor_StrongAuthTracker_clazz,
                "isUnlockingWithBiometricAllowed",
                true,
                Boolean::class.javaPrimitiveType
            )

            // 02.00 -------------------------------------------------------------------------------
            hook(
                Build.VERSION_CODES.N .. Build.VERSION_CODES.P,
                LockPatternUtils_StrongAuthTracker_clazz,
                "isFingerprintAllowedForUser",
                true,
                Int::class.javaPrimitiveType
            )

            // 02.00 - new -------------------------------------------------------------------------------
            hook(
                Build.VERSION_CODES.Q .. Build.VERSION_CODES.Q,
                LockPatternUtils_StrongAuthTracker_clazz,
                "isBiometricAllowedForUser",
                true,
                Int::class.javaPrimitiveType
            )

            hook(
                Build.VERSION_CODES.R.. Build.VERSION_CODES.CUR_DEVELOPMENT,
                LockPatternUtils_StrongAuthTracker_clazz,
                "isBiometricAllowedForUser",
                true,
                Boolean::class.javaPrimitiveType,
                Int::class.javaPrimitiveType
            )
        }
    }

    companion object {
        const val KeyguardUpdateMonitor_clazz = "com.android.keyguard.KeyguardUpdateMonitor"
        const val KeyguardUpdateMonitor_StrongAuthTracker_clazz = "com.android.keyguard.KeyguardUpdateMonitor\$StrongAuthTracker"
        const val LockPatternUtils_StrongAuthTracker_clazz = "com.android.internal.widget.LockPatternUtils\$StrongAuthTracker"
    }
}