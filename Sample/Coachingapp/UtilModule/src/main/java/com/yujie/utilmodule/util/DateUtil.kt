package com.yujie.utilmodule.util

import java.util.*

fun getThatDay(format: String) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONDAY) + 1
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    String.format(format, year, month, day)


}