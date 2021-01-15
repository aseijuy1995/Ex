package edu.yujie.socketex.ext

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.TypedValue
import java.io.*


//inSampleSize

fun File.calculateInSampleSize(inSampleSize: Int = 1): Bitmap {
    val options = BitmapFactory.Options()
    val bitmap = BitmapFactory.decodeFile(this.absolutePath, options)
    println("options: outWidth:${options.outWidth}, outHeight:${options.outHeight}")
    println("bitmap: width:${bitmap.width}, height:${bitmap.height}")
    return bitmap
}

fun Uri.calculateInSampleSize(context: Context, defaultSize: Int = 500): Bitmap? {
    val options = BitmapFactory.Options()
    val bitmap = BitmapFactory.decodeStream(context.contentResolver.openInputStream(this), null, options)
    println("options: outWidth:${options.outWidth}, outHeight:${options.outHeight}")
    if (options.outWidth > defaultSize || options.outHeight > defaultSize) {
//        options.inJustDecodeBounds = true
        val widthScale = options.outWidth / defaultSize
        val heightScale = options.outHeight / defaultSize
        val scale = Math.max(widthScale, heightScale)
        println("scale:$scale")
        options.inSampleSize = scale
//        options.inJustDecodeBounds = false
    }
    println("bitmap: width:${bitmap?.width}, height:${bitmap?.height}")
    println("bitmap: ${bitmap == null}")
    return bitmap
}

fun InputStream.calculateInSampleSize(inSampleSize: Int = 1): Bitmap? {
    val options = setInSampleSize(inSampleSize)
    return BitmapFactory.decodeStream(this, null, options)
}

fun Context.calculateInSampleSize(resId: Int, inSampleSize: Int): Bitmap {
    val options = setInSampleSize(inSampleSize)
    return BitmapFactory.decodeResource(resources, resId, options)
}

fun ByteArray.calculateInSampleSize(inSampleSize: Int): Bitmap {
    val options = setInSampleSize(inSampleSize)
    return BitmapFactory.decodeByteArray(this, 0, size, options)
}

fun FileDescriptor.calculateInSampleSize(inSampleSize: Int): Bitmap {
    val options = setInSampleSize(inSampleSize)
    return BitmapFactory.decodeFileDescriptor(this, null, options)
}

fun Context.calculateInSampleSize(value: TypedValue, stream: InputStream, inSampleSize: Int): Bitmap? {
    val options = setInSampleSize(inSampleSize)
    return BitmapFactory.decodeResourceStream(resources, value, stream, null, options)
}

private fun setInSampleSize(inSampleSize: Int): BitmapFactory.Options = BitmapFactory.Options().apply {
    this.inSampleSize = inSampleSize
}


fun Bitmap.compressBitmap(format: CompressFormat = CompressFormat.JPEG, quality: Int = 80): Bitmap {
    val baos = ByteArrayOutputStream()
    this.compress(format, quality, baos)
    val bytes: ByteArray = baos.toByteArray()
    return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
}

fun Bitmap.compressStream(format: CompressFormat = CompressFormat.JPEG, quality: Int = 100): InputStream {
    val baos = ByteArrayOutputStream()
    println("byteCount:${byteCount}")
    compress(format, quality, baos)
    println("baos.toByteArray().size:${baos.toByteArray().size}")
    val stream = ByteArrayInputStream(baos.toByteArray())
    return stream
}
