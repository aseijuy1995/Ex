package edu.yujie.socketex.ext

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.Rect
import android.net.Uri
import android.util.TypedValue
import java.io.*


/**
 * 采樣率壓縮
 * defaultMaxSize - 最大比例尺吋
 * */

data class BitmapConfig(
    val defaultMaxSize: Int = 1000,
    val colorConfig: Bitmap.Config = Bitmap.Config.ARGB_8888
)

fun File.getBitmap(config: BitmapConfig): Bitmap {
    val options = BitmapFactory.Options()
    BitmapFactory.decodeFile(absolutePath, options)
    setScaleMaxSize(options, config.defaultMaxSize)
    setColorConfig(options, config.colorConfig)
    val bitmap = BitmapFactory.decodeFile(absolutePath, options)
    return bitmap
}

fun Uri.getBitmap(contentResolver: ContentResolver, config: BitmapConfig): Bitmap? {
    val options = BitmapFactory.Options()
    BitmapFactory.decodeStream(contentResolver.openInputStream(this), null, options)
    setScaleMaxSize(options, config.defaultMaxSize)
    setColorConfig(options, config.colorConfig)
    val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(this), null, options)
    return bitmap
}

fun InputStream.getBitmap(config: BitmapConfig): Bitmap? {
    val options = BitmapFactory.Options()
    BitmapFactory.decodeStream(this, null, options)
    setScaleMaxSize(options, config.defaultMaxSize)
    setColorConfig(options, config.colorConfig)
    val bitmap = BitmapFactory.decodeStream(this, null, options)
    return bitmap
}

fun Context.getBitmap(resId: Int, config: BitmapConfig): Bitmap {
    val options = BitmapFactory.Options()
    BitmapFactory.decodeResource(resources, resId, options)
    setScaleMaxSize(options, config.defaultMaxSize)
    setColorConfig(options, config.colorConfig)
    val bitmap = BitmapFactory.decodeResource(resources, resId, options)
    return bitmap
}

fun ByteArray.getBitmap(config: BitmapConfig): Bitmap {
    val options = BitmapFactory.Options()
    BitmapFactory.decodeByteArray(this, 0, size, options)
    setScaleMaxSize(options, config.defaultMaxSize)
    setColorConfig(options, config.colorConfig)
    val bitmap = BitmapFactory.decodeByteArray(this, 0, size, options)
    return bitmap
}

fun FileDescriptor.getBitmap(config: BitmapConfig): Bitmap {
    val options = BitmapFactory.Options()
    BitmapFactory.decodeFileDescriptor(this, null, options)
    setScaleMaxSize(options, config.defaultMaxSize)
    setColorConfig(options, config.colorConfig)
    val bitmap = BitmapFactory.decodeFileDescriptor(this, null, options)
    return bitmap
}

fun Context.getBitmap(value: TypedValue, stream: InputStream, pad: Rect, config: BitmapConfig): Bitmap? {
    val options = BitmapFactory.Options()
    BitmapFactory.decodeResourceStream(resources, value, stream, null, options)
    setScaleMaxSize(options, config.defaultMaxSize)
    setColorConfig(options, config.colorConfig)
    val bitmap = BitmapFactory.decodeResourceStream(resources, value, stream, pad, options)
    return bitmap
}

private fun setScaleMaxSize(options: BitmapFactory.Options, defaultMaxSize: Int) {
    if (options.outWidth > defaultMaxSize || options.outHeight > defaultMaxSize) {
        val scale = (options.outWidth / defaultMaxSize).coerceAtLeast((options.outHeight / defaultMaxSize))
        setInSampleSize(options, scale)
    }
}

private fun setInSampleSize(options: BitmapFactory.Options, inSampleSize: Int = 1) = options.apply {
    inJustDecodeBounds = true
    this.inSampleSize = inSampleSize
    inJustDecodeBounds = false
}

fun setColorConfig(options: BitmapFactory.Options, inPreferredConfig: Bitmap.Config) = options.apply {
    this.inPreferredConfig = inPreferredConfig
}

/**
 * 質量壓縮
 * */

data class BitmapCompress(
    val quality: Int = 80,
    val format: CompressFormat = CompressFormat.JPEG
)

fun Bitmap.compressToByteArray(compress: BitmapCompress): ByteArray {
    val baos = ByteArrayOutputStream()
    compress(compress.format, compress.quality, baos)
    return baos.toByteArray()
}

fun Bitmap.compressToBitmap(compress: BitmapCompress): Bitmap {
    val byteArray = compressToByteArray(compress)
    return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
}

fun Bitmap.compressStream(compress: BitmapCompress): InputStream {
    val byteArray = compressToByteArray(compress)
    return ByteArrayInputStream(byteArray)
}

/**
 * 縮放壓縮
 */

fun Bitmap.scale(scale: Float = 0.5F): Bitmap {
    val matrix = Matrix()
    matrix.setScale(scale, scale)
    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}



