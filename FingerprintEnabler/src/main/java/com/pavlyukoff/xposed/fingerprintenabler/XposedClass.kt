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
                "com.android.keyguard.KeyguardUpdateMonitor",
                "isUnlockingWithFingerprintAllowed",
                true
            )

            // 00.01 - new -------------------------------------------------------------------------
            hook(
                Build.VERSION_CODES.Q .. Build.VERSION_CODES.Q,
                "com.android.keyguard.KeyguardUpdateMonitor",
                "isUnlockingWithBiometricAllowed",
                true
            )

            hook(
                Build.VERSION_CODES.R .. Build.VERSION_CODES.CUR_DEVELOPMENT,
                "com.android.keyguard.KeyguardUpdateMonitor",
                "isUnlockingWithBiometricAllowed",
                true,
                Boolean::class.javaPrimitiveType
            )

            // 00.02 -------------------------------------------------------------------------------
            hook(
                Build.VERSION_CODES.M .. Build.VERSION_CODES.CUR_DEVELOPMENT,
                "com.android.keyguard.KeyguardUpdateMonitor",
                "isUnlockWithFingerprintPossible",
                true,
                Int::class.javaPrimitiveType
            )

            // 00.03 -------------------------------------------------------------------------------
            hook(
                Build.VERSION_CODES.M .. Build.VERSION_CODES.CUR_DEVELOPMENT,
                "com.android.keyguard.KeyguardUpdateMonitor",
                "isFingerprintDisabled",
                false,
                Int::class.javaPrimitiveType
            )

            // 01.00 -------------------------------------------------------------------------------
            hook(
                Build.VERSION_CODES.M .. Build.VERSION_CODES.P,
                "com.android.keyguard.KeyguardUpdateMonitor\$StrongAuthTracker",
                "isUnlockingWithFingerprintAllowed",
                true
            )

            // 01.00 - new -------------------------------------------------------------------------------
            hook(
                Build.VERSION_CODES.Q .. Build.VERSION_CODES.Q,
                "com.android.keyguard.KeyguardUpdateMonitor\$StrongAuthTracker",
                "isUnlockingWithBiometricAllowed",
                true
            )

            hook(
                Build.VERSION_CODES.R .. Build.VERSION_CODES.CUR_DEVELOPMENT,
                "com.android.keyguard.KeyguardUpdateMonitor\$StrongAuthTracker",
                "isUnlockingWithBiometricAllowed",
                true,
                Boolean::class.javaPrimitiveType
            )

            // 02.00 -------------------------------------------------------------------------------
            hook(
                Build.VERSION_CODES.M .. Build.VERSION_CODES.P,
                "com.android.internal.widget.LockPatternUtils\$StrongAuthTracker",
                "isFingerprintAllowedForUser",
                true,
                Int::class.javaPrimitiveType
            )

            // 02.00 - new -------------------------------------------------------------------------------
            hook(
                Build.VERSION_CODES.Q .. Build.VERSION_CODES.Q,
                "com.android.internal.widget.LockPatternUtils\$StrongAuthTracker",
                "isBiometricAllowedForUser",
                true,
                Int::class.javaPrimitiveType
            )

            hook(
                Build.VERSION_CODES.R.. Build.VERSION_CODES.CUR_DEVELOPMENT,
                "com.android.internal.widget.LockPatternUtils\$StrongAuthTracker",
                "isBiometricAllowedForUser",
                true,
                Boolean::class.javaPrimitiveType,
                Int::class.javaPrimitiveType
            )
        }
    }
}