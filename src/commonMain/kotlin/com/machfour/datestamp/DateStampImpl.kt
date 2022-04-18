package com.machfour.datestamp

/*
 * Custom class for representing immutable Gregorian Calendar dates, without times.
 */
class DateStampImpl internal constructor(
    override val year: Int,
    override val month: Int,
    override val day: Int,
): DateStamp {

    private val hashCode = "$year$month$day".toInt()

    override fun equals(other: Any?) = (other is DateStamp)
            && year == other.year && month == other.month && day == other.day

    override fun hashCode(): Int {
        return hashCode
    }

    override fun toIso8601String(): String {
        return buildString {
            append(year)
            append("-")
            if (month in 1..9) {
                append(0)
            }
            append(month)
            append("-")
            if (day in 1..9) {
                append(0)
            }
            append(day)
        }

    }

    // Returns a new DateStamp representing the given increment of days from this one
    override fun step(dayIncrement: Long): DateStamp {
        return epochDayToYMD(epochDay() + dayIncrement)
    }

    override fun toString(): String = toIso8601String()

    override fun daysSince(): Long {
        return currentDateStamp().epochDay() - this.epochDay()
    }

    override fun compareTo(other: DateStamp): Int {
        // adapted from java LocalDate
        return when {
            year - other.year != 0 -> year - other.year
            month - other.month != 0 -> month - other.month
            else -> day - other.day
        }
    }

    override fun isInFuture(): Boolean = compareTo(currentDateStamp()) > 0

    override fun epochDay(): Long = ymdToEpochDay(year, month, day)


    // returns epoch millis of midnight on this date, in the UTC timezone
    override fun epochMillisAtMidnightUTC(): Long = epochMillisAtMidnight(0)

    // Copied from ChronoZonedDateTime.toEpochSecond
    override fun epochMillisAtMidnight(tzOffsetSeconds: Int): Long {
        return (epochDay() * 86400L - tzOffsetSeconds.toLong())*1000L
    }

    override fun epochMillisAtMidnightLocalTime(): Long {
        val tzOffset = currentTimezoneOffsetSeconds()
        return epochMillisAtMidnight(tzOffset)
    }
}
