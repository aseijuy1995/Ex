package edu.yujie.coroutinesex

/**
 * @author YuJie on 2020/10/18
 * @describe 說明
 * @param 參數
 */
sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()

    data class Error(val e: Exception) : Result<Nothing>()
}