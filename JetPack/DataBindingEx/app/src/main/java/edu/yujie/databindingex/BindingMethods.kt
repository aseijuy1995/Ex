package edu.yujie.databindingex

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods

/**
 * @author YuJie on 2020/10/18
 * @describe 說明
 * @param 參數
 */

@BindingMethods(
    value = [
        BindingMethod(
            type = TextView::class,
            attribute = "android:OnToastClick",
            method = "setOnToastClick"
        )
    ]
)
class TextView2(context: Context, attrs: AttributeSet) :
    androidx.appcompat.widget.AppCompatTextView(context, attrs) {
    private val TAG = javaClass.simpleName

    fun setOnToastClick(str: String) {
        setOnClickListener {
            val msg = "$TAG:setOnClick():$str"
            println(msg)
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }
    }

}