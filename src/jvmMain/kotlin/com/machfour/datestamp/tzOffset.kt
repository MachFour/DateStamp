package com.machfour.datestamp

import java.util.TimeZone

actual fun currentTimezoneOffsetSeconds(): Int {
    return TimeZone.getDefault().rawOffset / 1000
}