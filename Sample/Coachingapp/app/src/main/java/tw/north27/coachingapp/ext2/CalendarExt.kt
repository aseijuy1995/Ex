package tw.north27.coachingapp.ext2

import java.text.SimpleDateFormat
import java.util.*

/**
 * 取得日期時間
 * @param format：日期時間格式
 * @param locale：地區
 * */
fun Calendar.getDateTime(format: String, locale: Locale = Locale.getDefault()): String {
    val simpleDateFormat = SimpleDateFormat(format, locale)
    val str = simpleDateFormat.format(time)
    return str
}