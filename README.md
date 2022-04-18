# DateStamp

Basic usage:

    val date1 = makeDateStamp(2020, 1, 1) // direct construction
    val date2 = currentDateStamp() // uses midnight on current date in local (system) timezone
    val date3 = epochDateStamp(100000L) // creates a date corresponding to 100000 days after Jan 1, 1970



