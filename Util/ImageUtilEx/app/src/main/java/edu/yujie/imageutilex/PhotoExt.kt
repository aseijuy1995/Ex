package edu.yujie.imageutilex

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.TypedValue
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileDescriptor
import java.io.InputStream


//inSampleSize

fun File.calculateInSampleSize(inSampleSize: Int = 1): Bitmap {
    val options = setInSampleSize(inSampleSize)
    return BitmapFactory.decodeFile(this.absolutePath, options)
}

fun InputStream.calculateInSampleSize(inSampleSize: Int = 1): Bitmap? {
    val options = setInSampleSize(inSampleSize)
    return BitmapFactory.decodeStream(this, null, options)
}

fun Uri.calculateInSampleSize(context: Context, inSampleSize: Int): Bitmap? {
    val options = setInSampleSize(inSampleSize)
    return BitmapFactory.decodeStream(context.contentResolver.openInputStream(this), null, options)
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

fun Bitmap.compress(format: CompressFormat = CompressFormat.JPEG, quality: Int = 80): Bitmap {
    val baos = ByteArrayOutputStream()
    println("byteCount:${byteCount}")
    compress(format, quality, baos)
    println("baos.toByteArray().size:${baos.toByteArray().size}")
    val bytes: ByteArray = baos.toByteArray()
    val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    println("bitmap.byteCount:${bitmap.byteCount}")
    return bitmap
}