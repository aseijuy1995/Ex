package edu.yujie.databindingex

import androidx.databinding.InverseMethod

/**
 * @author YuJie on 2020/10/18
 * @describe 說明
 * @param 參數
 */
object Converter {

    @InverseMethod("stringToInt")
    fun intToString(number: Int): String = number.toString()

    fun stringToInt(str: String): Int {
        try {
            return str.toInt()
        } catch (e: Exception) {
            throw Exception("Can not converter string to int!")
        }
    }
}