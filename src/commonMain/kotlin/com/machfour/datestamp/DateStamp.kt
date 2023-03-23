package com.machfour.datestamp

// Gregorian date representation in local time (no timezone support)
interface DateStamp: Comparable<DateStamp> {
    // Gregorian calendar year
    val year: Int
    // month of year, 1-indexed
    val month: Int
    // day of month, 1-indexed
    val day: Int

    fun toIso8601String(): String

    // Hashcode based on year, month and day. Canonical hashcode is just concatenating the digits.
    override fun hashCode(): Int

    // Checks equality of year, month and day
    override fun equals(other: Any?): Boolean

    // Returns a new DateStamp representing the given increment of days from this one
    fun step(dayIncrement: Long): DateStamp

    /*
     * Converts this date into a number of days back from the today's date.
     * May return a negative number for dates in the future.
     */
    fun daysSince(): Long {
        return currentDateStamp().epochDay() - this.epochDay()
    }

    fun prettyPrint(): String {
        // TODO move to locale-specific area
        return buildString {
            val today = currentDateStamp()

            append(this@DateStamp.toString())
            when (this@DateStamp) {
                today -> append(" (today)")
                today.step(-1) -> append(" (yesterday)")
            }
        }
    }

    override fun compareTo(other: DateStamp): Int

    @Deprecated("use function version", ReplaceWith("isInFuture()"))
    val isInTheFuture: Boolean
        get() = isInFuture()

    fun isInFuture(): Boolean

    @Deprecated("use epochDay()", ReplaceWith("epochDay()"))
    val daysSince1Jan1970: Long
        get() = epochDay()

    fun epochDay(): Long

    // returns epoch millis of midnight on this date, in the local timezone
    fun epochMillisAtMidnightLocalTime(): Long

    // returns epoch millis of midnight on this date, in the UTC timezone
    fun epochMillisAtMidnightUTC(): Long

    // Copied from ChronoZonedDateTime.toEpochSecond
    fun epochMillisAtMidnight(tzOffsetSeconds: Int): Long

    companion object {
        // Creates a DateStamp corresponding to the current date in local time
        fun today() : DateStamp {
            return currentDateStamp()
        }

        // Creates a DateStamp corresponding to the given year, month and day
        fun of(year: Int, month: Int, day: Int): DateStamp {
            return makeDateStamp(year, month, day)
        }

        /*
         * Creates a DateStamp from an ISO-8601 formatted date string, * e.g "2017-08-01"
         * Throws IllegalArgumentException if the string is in an invalid format
         */
        fun ofIso8601String(iso8601Date: String): DateStamp {
            return iso8601StringDateStamp(iso8601Date)
        }

        /*
         * Creates a DateStamp corresponding to a number of days ago in local time.
         * This is equivalent to today().step(-1 * daysAgo)
         */
        fun ofDaysAgo(daysAgo: Long) = pastDateStamp(daysAgo)

        // Creates a new DateStamp corresponding to a number of days since Jan 1, 1970
        fun ofEpochDay(epochDay: Long) = epochDateStamp(epochDay)
    }
}