package tw.north27.coachingapp.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import timber.log.Timber
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream
import kotlin.math.roundToInt


data class BitmapOption(
    val isOptions: Boolean = true,
    val reqWidth: Int = 400,
    val reqHeight: Int = 800,
    val preferred: Bitmap.Config = Bitmap.Config.RGB_565//100 >> 不壓縮
)

data class BitmapCompress(
    val isCompress: Boolean = true,
    val format: Bitmap.CompressFormat = Bitmap.CompressFormat.PNG,
    val quality: Int = 80//100 >> 不壓縮
)

fun File.getBitmap(option: BitmapOption): Bitmap {
    val options = BitmapFactory.Options()
    if (option.isOptions) {
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(absolutePath, options)
        options.calculateInSampleSize(option.reqWidth, option.reqHeight)
        options.inJustDecodeBounds = false
    }
    val bitmap = BitmapFactory.decodeFile(absolutePath, options)
    preferredBitmap(option, options)
    Timber.d("bitmap.width = ${bitmap.width}, bitmap.height = ${bitmap.height}")
    return bitmap
}

/**
 * RGB_565：改變圖片大小（占用記憶體空間）但不影響寬高
 * */
private fun preferredBitmap(option: BitmapOption, options: BitmapFactory.Options) {
    if (option.isOptions) options.inPreferredConfig = option.preferred
}

/**
 * 圖片寬高縮放比
 * */
fun BitmapFactory.Options.calculateInSampleSize(reqWidth: Int, reqHeight: Int) {
    val width = outWidth
    val height = outHeight
    var sampleSize = 1
    Timber.d("outWidth = $width, outHeight = $height")
    if (width > reqWidth || height > reqHeight) {
        val heightRatio = (height.toFloat() / reqHeight.toFloat()).roundToInt()
        val widthRatio = (width.toFloat() / reqWidth.toFloat()).roundToInt()
        sampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
    }
    Timber.d("sampleSize = $sampleSize")
    inSampleSize = sampleSize
}

/**
 * 品質壓縮
 * */
fun Bitmap.toByteArray(compress: BitmapCompress): ByteArray {
    val baos = ByteArrayOutputStream()
    compressBitmap(compress, baos)
    return baos.toByteArray()
}

/**
 * 質量壓縮：改變圖片位深及透明度（不會減少圖片的寬高，像素）
 * */
fun Bitmap.compressBitmap(compress: BitmapCompress, baos: ByteArrayOutputStream) {
    val quality = if (compress.isCompress) compress.quality else 100
    compress(compress.format, quality, baos)
}

fun Bitmap.toInputStream(compress: BitmapCompress): InputStream {
    val byteArray = toByteArray(compress)
    return ByteArrayInputStream(byteArray)
}
