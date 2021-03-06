package com.yujie.utilmodule.adapter

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.yujie.utilmodule.R
import jp.wasabeef.glide.transformations.BlurTransformation
import java.text.SimpleDateFormat
import java.util.*

enum class SetType {
    IMAGE, BACKGROUND
}

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
    setType: SetType = SetType.IMAGE,
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
            if (setType == SetType.BACKGROUND) {
                into(object : SimpleTarget<Drawable>() {
                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                        background = resource
                    }
                })
            } else {
                into(this@bindImg)
            }
        }
}

/**
 * UI是否顯示
 * @param isVisible >> 是否顯示
 * */
@BindingAdapter("bind:isVisible")
fun View.bindVisible(isVisible: Boolean = true) {
    this.isVisible = isVisible
}

/**
 * 日期轉換為提問表顯示時間
 * @param dateString >> 日期字串
 * */
@BindingAdapter("bind:convDateToAskTime")
fun TextView.bindConvDateToAskTime(date: Date?) {

    val dateString = SimpleDateFormat("yyyy/MM/dd HH:mm").format(date)
}