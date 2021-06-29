package com.yujie.utilmodule.ext

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.io.File

fun ImageView.load(
		url: String?,
		file: File?,
		bitmap: Bitmap?,
		resId: Int?,
		byteArray: ByteArray?,
		drawable: Drawable?,
) {
		Glide.with(context).load
}