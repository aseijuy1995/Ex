package edu.yujie.databindingex

import android.graphics.drawable.ColorDrawable
import androidx.databinding.BindingConversion

/**
 * @author YuJie on 2020/10/18
 * @describe 說明
 * @param 參數
 */
@BindingConversion
fun colorToDrawable(color: Int) = ColorDrawable(color)