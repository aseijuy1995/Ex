package edu.yujie.socketex.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("app:imgRes")
fun ImageView.setImageRes(imgRes: Int) {
    setImageResource(imgRes)
}