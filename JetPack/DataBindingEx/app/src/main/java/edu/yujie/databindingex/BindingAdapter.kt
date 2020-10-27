package edu.yujie.databindingex

import android.view.View
import android.widget.Toast
import androidx.databinding.BindingAdapter

/**
 * @author YuJie on 2020/10/18
 * @describe 說明
 * @param 參數
 */

@BindingAdapter("android:OnToastClick")
fun View.setOnToastClick(str:String){
    setOnClickListener {
        val msg = "setOnToastClick():$str"
        println(msg)
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }
}