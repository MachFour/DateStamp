package com.machfour.datestamp

import kotlinx.cinterop.*
import platform.posix.localtime_r
import platform.posix.time
import platform.posix.tm

actual fun currentDateStamp(): DateStamp {
    val currentTime = time(null)

    val dateStamp = memScoped {
        val currentLocalTime = alloc<tm>()
        localtime_r(cValuesOf(currentTime), currentLocalTime.ptr)

        with(currentLocalTime) {
            makeDateStamp(tm_year, tm_mon, tm_mday)
        }
    }

    return dateStamp
}

