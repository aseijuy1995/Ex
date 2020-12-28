package edu.yujie.lohasapp.bind

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import edu.yujie.lohasapp.R

@BindingAdapter("app:bindImage")
//defaultRes: Int = R.drawable.ic_baseline_photo_size_select_actual_24_gray
fun ImageView.bindImage(url: String) {
    println("bindImage() $url")
    Glide.with(this).load(url).placeholder(R.drawable.ic_baseline_photo_size_select_actual_24_gray).into(this)
}