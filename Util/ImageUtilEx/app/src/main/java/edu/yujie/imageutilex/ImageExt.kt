package edu.yujie.imageutilex

import android.graphics.BitmapFactory
import android.os.Build
import android.widget.ImageView

fun ImageView.loadInfo(tag:String = "ImageView loadImage()") {
    val options = BitmapFactory.Options()
    val bitmap = BitmapFactory.decodeResource(resources, R.drawable.pic, options)
    println("$tag width:${bitmap.width}")
    println("$tag height:${bitmap.height}")
    println("$tag byteCount:${bitmap.byteCount}")
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        println("$tag allocationByteCount:${bitmap.allocationByteCount}")
    }
    println("$tag inDensity:${options.inDensity}")
    println("$tag inTargetDensity:${options.inTargetDensity}")
    println("$tag ImageView.width:${this.width}")
    println("$tag ImageView.height:${this.height}")

}