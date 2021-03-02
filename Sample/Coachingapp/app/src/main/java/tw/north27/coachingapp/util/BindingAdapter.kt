package tw.north27.coachingapp.util

import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import tw.north27.coachingapp.R

@BindingAdapter("bind:isVisibility")
fun View.bindVisibility(isVisibility: Boolean = true) {
    isVisible = isVisibility
}

@BindingAdapter("bind:imgUrl")
fun ImageView.bindImgUrl(url: String) {
    Glide.with(this).load(url).placeholder(R.mipmap.ic_pencil_logo).centerCrop().into(this)
}