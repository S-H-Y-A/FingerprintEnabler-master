package com.pavlyukoff.xposed.fingerprintenabler

import android.content.Context
import io.github.libxposed.api.XposedInterface
import io.github.libxposed.api.annotations.AfterInvocation
import io.github.libxposed.api.annotations.BeforeInvocation
import io.github.libxposed.api.annotations.XposedHooker
import java.lang.reflect.Member
import kotlin.random.Random

@XposedHooker
class ReturnConstReplace(private val result: Boolean) : XposedInterface.Hooker {
    companion object {
        @JvmStatic
        @BeforeInvocation
        fun beforeInvocation(method: Member, thisObject: Any?, args: Array<out Any>): XposedInterface.Hooker {
            val key = Random.nextInt()
            val appContext = args[0] as Context
            module.log("beforeInvocation: key = $key")
            module.log("app context: $appContext")
            module.log("xposed context: $this")
            return ReturnConstReplace(key)
        }

        @JvmStatic
        @AfterInvocation
        fun afterInvocation(extras: XposedInterface.Hooker, result: Any?) {
            val hooker = extras as ReturnConstReplace

        }
    }
}
