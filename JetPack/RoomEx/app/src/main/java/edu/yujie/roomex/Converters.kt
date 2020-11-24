package edu.yujie.roomex

import androidx.room.TypeConverter
import java.util.*

/**
 * @author YuJie on 2020/11/23
 * @describe 說明
 * @param 參數
 */
class Converters {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? = value?.let {
        Date(it)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? = date?.time?.toLong()

}