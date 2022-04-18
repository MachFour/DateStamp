package com.machfour.datestamp

fun makeDateStamp(year: Int, month: Int, day: Int): DateStamp {
    require(dayMonthIsValid(year, month, day))
    return DateStampImpl(year, month, day)
}

/*
 * Creates a new DateStamp instance from an ISO-8601 string, e.g '2017-08-01'
 * Throws IllegalArgumentException if the string is in an invalid format
 */
fun iso8601StringDateStamp(dateString: String): DateStamp {
    val split = dateString.split("-", limit = 3)

    if (split.size < 3) {
        throw illegalDate(dateString)
    }

    val year: Int
    val month: Int
    val day: Int

    try {
        year = split[0].toInt()
        month = split[1].toInt()
        day = split[2].toInt()
    } catch (e: NumberFormatException) {
        throw illegalDate(dateString)
    }

    if (!dayMonthIsValid(year, month, day)) {
        throw illegalDate(dateString)
    }

    return makeDateStamp(year, month, day)
}

/*
 * Get corresponding DateStamp for a number of days ago by using the Calendar's
 * field addition methods to add a negative amount of days
 */
fun pastDateStamp(daysAgo: Long): DateStamp = currentDateStamp().step(-1 * daysAgo)

// Creates a new DateStamp from a number of days since Jan 1, 1970
fun epochDateStamp(epochDay: Long): DateStamp {
    return epochDayToYMD(epochDay)
}


private fun illegalDate(dateString: String) =
    IllegalArgumentException("Date string not in ISO-8601 YYYY-MM-DD format: $dateString")

