package com.machfour.datestamp


internal fun dayMonthIsValid(year: Int, month: Int, day: Int): Boolean {
    val febDays = if (isLeapYear(year)) 29 else 28
    return when(month) {
        1, 3, 5, 7, 8, 10, 12 -> day in 1..31
        2 -> day in 1.. febDays
        4, 6, 9, 11 -> day in 1 .. 30
        else -> false
    }
}

// Copied from IsoChronology.isLeapYear
internal fun isLeapYear(prolepticYear: Int): Boolean {
    return (prolepticYear and 3) == 0 && (prolepticYear % 100 != 0 || prolepticYear % 400 == 0)
}
