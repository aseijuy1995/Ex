package edu.yujie.socketex.util

import android.text.format.DateFormat
import java.util.*

fun getTime(): String {
    val calendar = Calendar.getInstance(Locale.ENGLISH)
    calendar.timeInMillis = System.currentTimeMillis()
    val date = DateFormat.format("HH:mm", calendar).toString()
    return date
}