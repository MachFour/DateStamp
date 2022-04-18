package com.machfour.datestamp

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class BasicTests {

    @Test
    internal fun testCurrentDateStamp() {
        currentDateStamp()
    }

    @Test
    internal fun testFields() {
        val date = makeDateStamp(2020, 2, 2)
        assertEquals(2020, date.year, "Year mismatch")
        assertEquals(2, date.month, "Month mismatch")
        assertEquals(2, date.day, "Day mismatch")
    }

    @Test
    internal fun testCurrentTzOffset() {
        print("Current TZ offset seconds: ${currentTimezoneOffsetSeconds()}\n")

    }

    @Test
    internal fun testEpochDateStamp() {
        assertEquals(
            expected = makeDateStamp(1970, 1, 1),
            actual = epochDateStamp(0L),
            message = "Constructing datestamp from epoch day failed"
        )
        assertEquals(
            expected = makeDateStamp(1970, 1, 2),
            actual = epochDateStamp(1L),
            message = "Constructing datestamp from epoch day failed"
        )
        assertEquals(
            expected = makeDateStamp(1969, 12, 31),
            actual = epochDateStamp(-1L),
            message = "Constructing datestamp from epoch day failed"
        )
    }

    @Test
    internal fun testToString() {
        assertEquals(
            expected = "2020-02-02",
            actual = makeDateStamp(2020, 2, 2).toString(),
            message = "toString() failed"
        )
        assertEquals(
            expected = "2020-12-31",
            actual = makeDateStamp(2020, 12, 31).toString(),
            message = "toString() failed"
        )
    }

    @Test
    internal fun testFromString() {
        assertEquals(
            expected = makeDateStamp(2020, 12, 31),
            actual = iso8601StringDateStamp("2020-12-31"),
            message = "Parsing ISO-8601 string failed"
        )
        assertEquals(
            expected = makeDateStamp(2200, 2, 1),
            actual = iso8601StringDateStamp("2200-02-1"),
            message = "Parsing ISO-8601 string failed"
        )
    }

    @Test
    internal fun testStep() {
        assertEquals(
            expected = makeDateStamp(2020, 12, 31),
            actual = makeDateStamp(2021, 1, 1).step(-1),
            message = "step(-1) failed"
        )
        assertEquals(
            expected = makeDateStamp(2021, 1, 1),
            actual = makeDateStamp(2020, 12, 31).step(1),
            message = "step(+1) failed"
        )
    }

    @Test
    internal fun testValidDirectConstruction() {
        makeDateStamp(2020, 2, 29)
        makeDateStamp(2000, 2, 29)
        makeDateStamp(2019, 2, 28)
        makeDateStamp(2029, 8, 29)
        makeDateStamp(2009, 1, 31)
        makeDateStamp(2011, 4, 30)
    }

    @Test
    internal fun testInvalidDirectConstruction() {
        assertFails("Invalid date") { makeDateStamp(2019, 2, 29) }
        assertFails("Invalid date") { makeDateStamp(2100, 2, 29) }
        assertFails("Invalid date") { makeDateStamp(2009, 2, 31) }
        assertFails("Invalid date") { makeDateStamp(2009, 11, 31) }
        assertFails("Invalid date") { makeDateStamp(-222222020, 20, 20) }
        assertFails("Invalid date") { makeDateStamp(2020, 2, 0) }
        assertFails("Invalid date") { makeDateStamp(2020, 0, 2) }
        assertFails("Invalid date") { makeDateStamp(0, 0, 0) }
    }

    @Test
    internal fun testValidStringConstruction() {
        iso8601StringDateStamp("2020-02-20")
        iso8601StringDateStamp("2029-08-10")
        iso8601StringDateStamp("2009-12-31")
        iso8601StringDateStamp("2001-11-30")
    }

    @Test
    internal fun testInvalidYearStringConstruction() {
        assertFails("Invalid date") { iso8601StringDateStamp("2009-02-31") }
        assertFails("Invalid date") { iso8601StringDateStamp("2009-11-31") }
        assertFails("Invalid date") { iso8601StringDateStamp("-22222020-20-20") }
        assertFails("Invalid date") { iso8601StringDateStamp("2009-02-00") }
        assertFails("Invalid date") { iso8601StringDateStamp("2029-00-02") }
        assertFails("Invalid date") { iso8601StringDateStamp("0-0-0") }
    }
}