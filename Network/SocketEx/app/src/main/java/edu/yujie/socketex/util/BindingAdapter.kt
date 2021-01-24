package edu.yujie.socketex.util

import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.ui.PlayerView
import edu.yujie.socketex.R
import okio.ByteString

@BindingAdapter("bind:imgSrc")
fun ImageView.bindImgSrc(imgSrc: Int) {
    Glide.with(context).load(imgSrc).placeholder(R.drawable.ic_baseline_photo_24_gray).into(this)
}


@BindingAdapter("bind:imgByteArray")
fun ImageView.bindImgByteArray(byteArray: ByteArray) {
    Glide.with(context).load(byteArray).placeholder(R.drawable.ic_baseline_photo_24_gray).into(this)
}

@BindingAdapter("bind:videoByte")
fun PlayerView.bindVideoByteArray(byte: Byte) {
//    val mediaItem = MediaItem.fromUri()
//     SimpleExoPlayer.Builder(context).build().apply {
//        setMediaItem(mediaItem)
//        repeatMode = Player.REPEAT_MODE_ALL
//        prepare()
//        play()
//    }
}

////////////////////////////////////////////////

@BindingAdapter("bind:imageByteString")
fun ImageView.imageByteString(byteString: ByteString?) {
    val byteArray = byteString?.toByteArray()
    Glide.with(context).load(byteArray).placeholder(R.drawable.ic_baseline_photo_24_gray).into(this)
}

@BindingAdapter("bind:imgPath")
fun ImageView.imgPath(path: String?) {
    Glide.with(context).load(path).placeholder(R.drawable.ic_baseline_photo_24_gray).into(this)
}


@BindingAdapter("bind:isVisible")
fun View.isVisible(isVisible: Boolean) {
    this.isVisible = isVisible
}
