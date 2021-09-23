package com.pavlyukoff.xposed.fingerprintenabler

import android.os.Build
import android.util.Log
import de.robv.android.xposed.XC_MethodReplacement
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers

class HookHelper(private val classLoader: ClassLoader) {
    private val isDebug = BuildConfig.DEBUG


    private fun LogE(
        e: Throwable
    ) {
        XposedBridge.log("[$TAG] ${Log.getStackTraceString(e)}")

        if (!isDebug) return

        Log.e(TAG, "Exception", e)
    }

    private fun LogE(
        hooked: Boolean,
        className: String,
        methodName: String
    ) {
        val logText = "[$TAG] Hook : ${if (hooked) "YES" else "no"} : $methodName : $className"
        XposedBridge.log(logText)

        if (!isDebug) return

        Log.e(TAG, logText)
    }

    @Throws(Throwable::class)
    fun hook(
        apiRange: IntRange,
        className: String,
        methodName: String,
        result: Boolean,
        parameterTypes: Any?
    ) {
        hook(apiRange, className, methodName) {
            XposedHelpers.findAndHookMethod(
                className,
                classLoader,
                methodName,
                parameterTypes, XC_MethodReplacement.returnConstant(result)
            )
        }
    }

    @Throws(Throwable::class)
    fun hook(
        apiRange: IntRange,
        className: String,
        methodName: String,
        result: Boolean,
        parameterTypes: Any?,
        parameterTypes2: Any?
    ) {
        hook(apiRange, className, methodName) {
            XposedHelpers.findAndHookMethod(
                className,
                classLoader,
                methodName,
                parameterTypes, parameterTypes2, XC_MethodReplacement.returnConstant(result)
            )
        }
    }

    @Throws(Throwable::class)
    fun hook(
        apiRange: IntRange,
        className: String,
        methodName: String,
        result: Boolean
    ) {
        hook(apiRange, className, methodName) {
            XposedHelpers.findAndHookMethod(
                className,
                classLoader,
                methodName,
                XC_MethodReplacement.returnConstant(result)
            )
        }
    }

    @Throws(Throwable::class)
    private fun hook(
        apiRange: IntRange,
        className: String,
        methodName: String,
        hookProcess: () -> Unit
    ) {
        if (Build.VERSION.SDK_INT !in apiRange) return

        var hooked = true
        try {
            hookProcess()
        } catch (e: Throwable) {
            hooked = false
            LogE(e)
        }
        LogE(hooked, className, methodName)
    }

    companion object {
        private const val TAG = "FPE"
    }

}