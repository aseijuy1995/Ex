package com.yujie.utilmodule.ext

import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.LifecycleOwner
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.checkedChanges
import com.yujie.utilmodule.R
import jp.wasabeef.glide.transformations.BlurTransformation
import java.util.concurrent.TimeUnit

fun View.clicksObserve(windowDuration: Long = 500, unit: TimeUnit = TimeUnit.MILLISECONDS, owner: LifecycleOwner, onNext: (Unit) -> Unit) =
    clicks().throttleFirst(windowDuration, unit).observe(owner, onNext)

fun SwitchCompat.checkedChangesObserve(windowDuration: Long = 500, unit: TimeUnit = TimeUnit.MILLISECONDS, owner: LifecycleOwner, onNext: (Boolean) -> Unit) =
    checkedChanges().throttleFirst(windowDuration, unit).observe(owner, onNext)