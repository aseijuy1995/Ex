package tw.north27.coachingapp.util

import android.graphics.Color
import android.graphics.Typeface
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import tw.north27.coachingapp.R
import tw.north27.coachingapp.model.result.ChatInfo
import tw.north27.coachingapp.model.result.ChatRead


@BindingAdapter("bind:isVisibility")
fun View.bindVisibility(isVisibility: Boolean = true) {
    isVisible = isVisibility
}

@BindingAdapter("bind:imgUrl")
fun ImageView.bindImgUrl(url: String) {
    Glide.with(this).load(url).placeholder(R.mipmap.ic_pencil_logo).centerCrop().into(this)
}

//
@BindingAdapter("bind:isReadColor")
fun View.bindIsReadColor(isRead: Boolean) {
    setBackgroundColor(if (isRead) resources.getColor(R.color.yellow_fef5e0) else Color.WHITE)
}

/**
 * 通知開關
 * */
@BindingAdapter("bind:isNotifyOpen")
fun ImageView.bindIsReadColor(isOpen: Boolean) {
    setImageResource(if (isOpen) R.drawable.ic_baseline_notifications_24_white else R.drawable.ic_baseline_notifications_off_24_white)
}

/**
 * 聊天列表文字樣式
 * */
@BindingAdapter("bind:chatTextStyle")
fun TextView.bindChatTextStyle(read: ChatRead) {
    when (read) {
        ChatRead.HAVE_READ -> setTypeface(null, Typeface.NORMAL)
        ChatRead.UN_READ -> setTypeface(null, Typeface.BOLD)
    }
}

@BindingAdapter("bind:chatBadgeStyle")
fun TextView.bindChatBadgeStyle(chat: ChatInfo) {
    when (chat.read) {
        ChatRead.HAVE_READ -> {
            isVisible = false
        }
        ChatRead.UN_READ -> {
            isVisible = true
            text = if (chat.unReadCount >= 100) "99+" else chat.unReadCount.toString()
        }
    }
}

/**
 * 聊天聲音狀態
 * */
@BindingAdapter("bind:imgSoundRes")
fun ImageView.bindImgSoundRes(isSound: Boolean) {
    setImageResource(
        if (isSound)
            R.drawable.ic_baseline_volume_up_24_white
        else
            R.drawable.ic_baseline_volume_off_24_white
    )
}