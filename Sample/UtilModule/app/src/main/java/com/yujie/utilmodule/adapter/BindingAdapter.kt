package com.yujie.utilmodule.adapter

import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.yujie.utilmodule.R
import jp.wasabeef.glide.transformations.BlurTransformation

/**
 * 圖片高斯模糊
 * @param resId >> 圖片res
 * @param url >> 圖片url
 * @param placeRes >> 預設圖片res
 * @param blurRadius >> 模糊半徑
 * @param blurSampling >> 模糊採樣
 * @param roundingRadius >> 圓角
 * */
@BindingAdapter(
		value = [
				"bind:imgRes",
				"bind:imgUrl",
				"bind:imgPlaceRes",
				"bind:imgBlurRadius",
				"bind:imgBlurSampling",
				"bind:imgRoundingRadius",
		]
)
fun ImageView.bindImg(
		@DrawableRes resId: Int? = null,
		url: String? = null,
		@DrawableRes placeRes: Int = R.drawable.ic_baseline_photo_24_gray,
		blurRadius: Int? = null,
		blurSampling: Int? = null,
		roundingRadius: Int? = null
) {
		Glide.with(this)
				.load(
						resId
								?: url
								?: ""
				)
				.placeholder(placeRes)
				.apply {
						if (blurRadius != null && blurSampling != null) RequestOptions.bitmapTransform(BlurTransformation(blurRadius, blurSampling))
						if (roundingRadius != null) RequestOptions.bitmapTransform(RoundedCorners(roundingRadius))
				}
				.into(this)
}

/**
 * UI是否顯示
 * @param isVisible >> 是否顯示
 * */
@BindingAdapter("bind:isVisible")
fun View.bindVisible(isVisible: Boolean = true) {
		this.isVisible = isVisible
}