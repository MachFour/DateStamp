package com.machfour.datestamp

// Copied from Java LocalDate.ofEpochDay
// commit: 5691a3b6afcb3229ccd0e00d3a4ec9ccacc93182

/**
 * The number of days in a 400 year cycle.
 */
private const val DAYS_PER_CYCLE = 146097L
/**
 * The number of days from year zero to year 1970.
 * There are five 400 year cycles from year zero to 2000.
 * There are 7 leap years from 1970 to 2000.
 */
private const val DAYS_0000_TO_1970 = (DAYS_PER_CYCLE * 5L) - (30L * 365L + 7L)

// Original function: LocalDate.ofEpochDay()
internal fun epochDayToYMD(epochDay: Long): DateStamp {
    require(epochDay in -365243219162L .. 365241780471L) { "Invalid epoch day: $epochDay" }

    var zeroDay: Long = epochDay + DAYS_0000_TO_1970
    // find the march-based year
    zeroDay -= 60 // adjust to 0000-03-01 so leap day is at end of four year cycle

    var adjust: Long = 0
    if (zeroDay < 0) {
        // adjust negative years to positive for calculation
        val adjustCycles: Long = (zeroDay + 1) / DAYS_PER_CYCLE - 1
        adjust = adjustCycles * 400
        zeroDay += -adjustCycles * DAYS_PER_CYCLE
    }
    var yearEst: Long = (400 * zeroDay + 591) / DAYS_PER_CYCLE
    var doyEst = zeroDay - (365 * yearEst + yearEst / 4 - yearEst / 100 + yearEst / 400)
    if (doyEst < 0) {
        // fix estimate
        yearEst--
        doyEst = zeroDay - (365 * yearEst + yearEst / 4 - yearEst / 100 + yearEst / 400)
    }
    yearEst += adjust // reset any negative year

    val marchDoy0 = doyEst.toInt()

    // convert march-based values back to january-based

    // convert march-based values back to january-based
    val marchMonth0 = (marchDoy0 * 5 + 2) / 153
    var month = marchMonth0 + 3
    if (month > 12) {
        month -= 12
    }
    val dom = marchDoy0 - (marchMonth0 * 306 + 5) / 10 + 1
    if (marchDoy0 >= 306) {
        yearEst++
    }

    check(yearEst in -999999999L .. 999999999L) { "Year out of range: $yearEst" }
    return makeDateStamp(yearEst.toInt(), month, dom)
}

// Original function: LocalDate.ofEpochDay()
internal fun ymdToEpochDay(year: Int, month: Int, day: Int): Long {
    val y: Long = year.toLong()
    val m: Long = month.toLong()
    var total: Long = 0
    total += 365 * y
    if (y >= 0) {
        total += (y + 3) / 4 - (y + 99) / 100 + (y + 399) / 400
    } else {
        total -= y / -4 - y / -100 + y / -400
    }
    total += (367 * m - 362) / 12
    total += day - 1
    if (m > 2) {
        total--
        if (!isLeapYear(year)) {
            total--
        }
    }
    return total - DAYS_0000_TO_1970
}
