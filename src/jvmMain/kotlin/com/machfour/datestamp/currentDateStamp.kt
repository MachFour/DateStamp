package com.machfour.datestamp

import java.time.LocalDate

actual fun currentDateStamp(): DateStamp {
    with(LocalDate.now()) {
        return DateStampImpl(year, monthValue, dayOfMonth)
    }
}

