package com.machfour.datestamp

interface DateStamp: Comparable<DateStamp> {
    val year: Int
    val month: Int
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
}