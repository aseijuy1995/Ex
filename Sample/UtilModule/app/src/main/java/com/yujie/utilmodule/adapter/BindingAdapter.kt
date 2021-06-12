package com.yujie.utilmodule.adapter

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.BlurTransformation

/**
 * 圖片高斯模糊
 * @param drawableResId >> Resource資源（bind:imgBlurRes）
 * @param radius >> （bind:imgBlurRadius）
 * @param sampling >> 採樣(模糊)（bind:imgBlurSampling）
 * */
@BindingAdapter(
    value = [
        "bind:imgRes",
        "bind:imgUrl",
        "bind:blurRadius",
        "bind:blurSampling"
    ]
)
fun ImageView.bindImg(
    @DrawableRes resId: Int? = null,
    url: String? = null,
    radius: Int = 1,
    sampling: Int = 1
) {
    println("resId:${resId == null}, url:${url == null}")
    Glide.with(this)
        .load(resId ?: url ?: "")
        .apply(RequestOptions.bitmapTransform(BlurTransformation(radius, sampling)))
        .into(this)
}