package com.machfour.datestamp

import kotlinx.cinterop.*
import platform.posix.localtime_r
import platform.posix.time
import platform.posix.tm

internal actual fun currentTimezoneOffsetSeconds(): Int {
    val currentTime = time(null)

    val currentTzOffset = memScoped {
        val currentLocalTime = nativeHeap.alloc<tm>()
        localtime_r(cValuesOf(currentTime), currentLocalTime.ptr)

        currentLocalTime.tm_gmtoff
    }

    return currentTzOffset.toInt()
}