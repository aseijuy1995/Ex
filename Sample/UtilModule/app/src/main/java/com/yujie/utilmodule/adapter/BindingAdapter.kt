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
				"bind:blurRadius",
				"bind:blurSampling"
		]
)
fun ImageView.bindImgRes(
		@DrawableRes resId: Int,
		radius: Int = 15,
		sampling: Int = 3
) {
		Glide.with(this)
				.load(resId)
				.apply(RequestOptions.bitmapTransform(BlurTransformation(radius, sampling)))
				.into(this)
}