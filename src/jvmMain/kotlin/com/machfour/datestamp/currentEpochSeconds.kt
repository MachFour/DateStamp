package com.machfour.datestamp

actual fun currentEpochSeconds(): Long {
    return System.currentTimeMillis() / 1000L
}