package edu.yujie.databindingex

import androidx.databinding.BaseObservable

/**
 * @author YuJie on 2020/10/17
 * @describe 說明
 * @param 參數
 */
data class User(
    var firstName: String,
    val lastName: String,
    val isAdult: Boolean
) : BaseObservable()