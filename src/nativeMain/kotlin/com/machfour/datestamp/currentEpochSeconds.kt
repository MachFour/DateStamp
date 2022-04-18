package com.machfour.datestamp

import platform.posix.time

actual fun currentEpochSeconds(): Long {
    return time(null)
}