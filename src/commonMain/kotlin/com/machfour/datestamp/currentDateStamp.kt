package com.machfour.datestamp

// DateStamp representing the current date in the local timezone
private const val SECONDS_IN_DAY = 86400L

fun currentDateStamp(): DateStamp {
    val localSecond = currentEpochSeconds() + currentTimezoneOffsetSeconds().toLong()
    val localEpochDay = localSecond.floorDiv(SECONDS_IN_DAY)
    return epochDateStamp(localEpochDay)
}
