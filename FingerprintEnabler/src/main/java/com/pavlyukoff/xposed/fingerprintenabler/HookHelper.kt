package com.pavlyukoff.xposed.fingerprintenabler

import android.os.Build
import android.util.Log

class HookHelper(
    private val classLoader: ClassLoader,
    private val module: XposedClass
) {
    private val isDebug = true


    private fun LogE(
        e: Throwable
    ) {
        module.log("[$TAG] ${Log.getStackTraceString(e)}")

        if (!isDebug) return

        Log.e(TAG, "Exception", e)
    }

    private fun LogE(
        hooked: Boolean,
        className: String,
        methodName: String
    ) {
        val logText = StringBuilder().apply {
            append("[${TAG}] ")
            append("Hook : ")
            if (hooked) {
                append("YES")
            } else {
                append("NO")
            }
            append(" : $methodName : $className")
        }.toString()
        module.log(logText)

        if (!isDebug) return

        Log.e(TAG, logText)
    }

    @Throws(Throwable::class)
    fun hook(
        apiRange: IntRange,
        className: String,
        methodName: String,
        result: Boolean,
        vararg parameters: Class<out Any?>? = emptyArray()
    ) {

        if (Build.VERSION.SDK_INT !in apiRange) return

        var hooked = true
        try {
            module.hook(
                classLoader.loadClass(className).getDeclaredMethod(methodName, *parameters),
                ReturnConstReplace(result).javaClass
            )
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
