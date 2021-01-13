package edu.yujie.socketex.util

import android.graphics.BitmapFactory
import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import edu.yujie.socketex.R
import okio.ByteString

@BindingAdapter("bind:imgRes")
fun ImageView.bindImgRes(imgRes: Int) {
    setImageResource(imgRes)
}

@BindingAdapter("app:imageByteString")
fun ImageView.imageByteString(byteString: ByteString?) {
    val byteArray = byteString?.toByteArray()
    Glide.with(context).load(byteArray).placeholder(R.drawable.ic_baseline_photo_24_blue).into(this)
}

@BindingAdapter("app:isVisible")
fun View.isVisible(isVisible: Boolean) {
    this.isVisible = isVisible
}

@BindingAdapter("app:imgByteArray")
fun ImageView.setImageByteArray(byteArray: ByteArray) {
    val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)

    println("bitmap: width:${bitmap.width}, height:${bitmap.height}")
    setImageBitmap(bitmap)
}