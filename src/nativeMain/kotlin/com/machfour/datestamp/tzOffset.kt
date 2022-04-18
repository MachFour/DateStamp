package com.machfour.datestamp

import kotlinx.cinterop.*
import platform.posix.localtime_r
import platform.posix.tm

internal actual fun currentTimezoneOffsetSeconds(): Int {
    val secondsSinceEpoch = currentEpochSeconds()

    val currentTzOffset = memScoped {
        val currentLocalTime = nativeHeap.alloc<tm>()
        localtime_r(cValuesOf(secondsSinceEpoch), currentLocalTime.ptr)
        currentLocalTime.tm_gmtoff
    }

    return currentTzOffset.toInt()
}