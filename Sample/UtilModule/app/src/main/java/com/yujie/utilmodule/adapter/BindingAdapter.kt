package com.yujie.utilmodule.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.yujie.utilmodule.R
import jp.wasabeef.glide.transformations.BlurTransformation
import java.util.*

//enum class SetType {
//		IMAGE, BACKGROUND
//}

/**
 * 圖片高斯模糊
 * @param resId >> 圖片res
 * @param url >> 圖片url
 * @param placeRes >> 預設圖片res
 * @param blurRadius >> 模糊半徑
 * @param blurSampling >> 模糊採樣
 * @param roundingRadius >> 圓角
 * */
fun ImageView.bindImg(
		@DrawableRes resId: Int? = null,
		url: String? = null,
		@DrawableRes placeRes: Int = R.drawable.ic_baseline_photo_24_gray,
		blurRadius: Int? = null,
		blurSampling: Int? = null,
		roundingRadius: Int? = null,
//		setType: SetType = SetType.IMAGE,
) {
		Glide.with(this)
				.load(
						resId
								?: url
								?: ""
				)
				.placeholder(placeRes)
				.apply {
						if (blurRadius != null && blurSampling != null) apply(RequestOptions.bitmapTransform(BlurTransformation(blurRadius, blurSampling)))
						if (roundingRadius != null) apply(RequestOptions.bitmapTransform(RoundedCorners(roundingRadius)))
//						if (setType == SetType.BACKGROUND) {
//								into(object : SimpleTarget<Drawable>() {
//										override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
//												background = resource
//										}
//								})
//						} else {
						into(this@bindImg)
//						}
				}
}

/**
 * UI是否顯示
 * @param isVisible >> 是否顯示
 * */
@BindingAdapter("android:isVisible")
fun View.bindVisible(isVisible: Boolean = true) {
		this.isVisible = isVisible
}

/**
 * 日期轉換為提問表顯示時間
 * @param date >> 日期
 * */
@BindingAdapter("android:convDateToTime")
fun TextView.bindConvDateToTime(date: Date?) {
		val calendar = Calendar.getInstance()
		calendar.time = date
		val hour = calendar.get(Calendar.HOUR_OF_DAY)
		val minute = calendar.get(Calendar.MINUTE)
		val sf = String.format("%s:%s", if (hour < 10) "0$hour" else hour.toString(), if (minute < 10) "0$minute" else minute.toString())
		text = sf
}