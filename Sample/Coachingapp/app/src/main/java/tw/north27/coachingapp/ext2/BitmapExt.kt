package tw.north27.coachingapp.ext2

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import timber.log.Timber
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream
import kotlin.math.roundToInt

/**
 * isOptions：是否壓縮
 * reqWidth：寬度設置
 * reqHeight：高度設置
 * preferred：畫質
 * */
data class BitmapOption(
    val isOptions: Boolean = true,
    val reqWidth: Int = 400,
    val reqHeight: Int = 800,
    val preferred: Bitmap.Config = Bitmap.Config.RGB_565//100 >> 不壓縮
)

/**
 * isCompress：是否壓縮
 * format：圖片格式
 * quality：質量 - 100 >> 不壓縮
 * */
data class BitmapCompress(
    val isCompress: Boolean = true,
    val format: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG,
    val quality: Int = 80
)

fun File.getBitmap(option: BitmapOption): Bitmap {
    val options = BitmapFactory.Options()
    if (option.isOptions) {
        preferredBitmap(option, options)
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(absolutePath, options)
        options.inSampleSizeBitmap(option.reqWidth, option.reqHeight)
        options.inJustDecodeBounds = false
    }
    val bitmap = BitmapFactory.decodeFile(absolutePath, options)
    Timber.d(
        """
        |byteCount = ${bitmap.byteCount}
        |width = ${bitmap.width}, height = ${bitmap.height}
    """.trimMargin("|")
    )
    return bitmap
}

/**
 * RGB_565：改變圖片大小（byteCount：占用記憶體空間）但不影響寬高
 * */
private fun preferredBitmap(option: BitmapOption, options: BitmapFactory.Options) {
    options.inPreferredConfig = option.preferred
}

/**
 * 采樣率壓縮：改變圖片寬高，大小（byteCount：占用記憶體空間），依比例原則
 * */
fun BitmapFactory.Options.inSampleSizeBitmap(reqWidth: Int, reqHeight: Int) {
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

fun Bitmap.toByteArray(compress: BitmapCompress): ByteArray {
    val baos = ByteArrayOutputStream()
    compressBitmap(compress, baos)
    return baos.toByteArray()
}

fun Bitmap.toInputStream(compress: BitmapCompress): InputStream {
    val byteArray = toByteArray(compress)
    return ByteArrayInputStream(byteArray)
}

/**
 * 質量壓縮：改變圖片位深及透明度，但不影響寬高，像素
 * */
fun Bitmap.compressBitmap(compress: BitmapCompress, baos: ByteArrayOutputStream) {
    val quality = if (compress.isCompress) compress.quality else 100
    compress(compress.format, quality, baos)
}
