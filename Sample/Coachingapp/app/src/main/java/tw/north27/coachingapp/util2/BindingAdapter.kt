package tw.north27.coachingapp.util2

import android.graphics.Color
import android.graphics.Typeface
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.yujie.prefmodule.dataStore.dataStoreUserPref
import com.yujie.prefmodule.dataStore.getAccount
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import tw.north27.coachingapp.R
import tw.north27.coachingapp.media.mediaStore.Media
import tw.north27.coachingapp.model.result.ChatInfo
import tw.north27.coachingapp.model.result.ChatRead

/**
 * 圖片高斯模糊
 * */
@BindingAdapter(value = ["bind:imgBlurRes", "bind:blurRadius", "bind:blurSampling"])
fun ImageView.bindImgBlurRes(resId: Int, radius: Int = 15, sampling: Int = 5) {
    Glide.with(this)
        .load(resId)
        .apply(RequestOptions.bitmapTransform(BlurTransformation(radius, sampling)))
        .into(this)
}

//------------------------------------------------------------------------------------------------------------------------------

/**
 * ChatRoom
 * */
@BindingAdapter("bind:chatRoomTitleText")
fun TextView.bindChatRoomTitleText(chat: ChatInfo) {
    val account = runBlocking { context.dataStoreUserPref.getAccount().first() }
    text = when (account) {
        chat.sender.account -> chat.recipient.name
        chat.recipient.account -> chat.sender.name
        else -> ""
    }
}


/**
 * 分割線
 * */

@BindingAdapter("bind:isVisibility")
fun View.bindVisibility(isVisibility: Boolean = true) {
    isVisible = isVisibility
}

@BindingAdapter("bind:imgUrl")
fun ImageView.bindImgUrl(url: String) {
    Glide.with(this).load(url).placeholder(R.mipmap.ic_pencil_logo).centerCrop().into(this)
}

@BindingAdapter("bind:imgRes")
fun ImageView.bindImgRes(resId: Int) {
    setImageResource(resId)
}

@BindingAdapter("bind:imgByteArray")
fun ImageView.bindImgByteArray(byteArray: ByteArray) {
    Glide.with(context).load(byteArray).placeholder(R.drawable.ic_coffee_logo).into(this)
}

//
@BindingAdapter("bind:isReadColor")
fun View.bindIsReadColor(isRead: Boolean) {
    setBackgroundColor(if (isRead) resources.getColor(R.color.yellow_fef5e0) else Color.WHITE)
}

//

/**
 * Chat
 * */
/**
 * 聊天列表頭貼
 * */
@BindingAdapter("bind:chatListImageAvatarUrl")
fun ImageView.bindChatListImageAvatarUrl(chat: ChatInfo) {
    val account = runBlocking { context.dataStoreUserPref.getAccount().first() }
    val avatarPath = when (account) {
        chat.sender.account -> chat.recipient.avatarPath
        chat.recipient.account -> chat.sender.avatarPath
        else -> ""
    }
    bindImgUrl(avatarPath)
}

/**
 * 聊天列表標題
 * */
@BindingAdapter("bind:chatListTitleText")
fun TextView.bindChatListTitleText(chat: ChatInfo) {
    val account = runBlocking { context.dataStoreUserPref.getAccount().first() }

    val name = when (account) {
        chat.sender.account -> chat.recipient.name
        chat.recipient.account -> chat.sender.name
        else -> ""
    }
    text = name
}

/**
 * 聊天聲音狀態
 * */
@BindingAdapter("bind:chatListSoundImage")
fun ImageView.bindChatListSoundImage(isSound: Boolean) {
    setImageResource(
        if (isSound)
            R.drawable.ic_baseline_volume_up_24_white
        else
            R.drawable.ic_baseline_volume_off_24_white
    )
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

/**
 * 聊天列表提示徽章
 * */
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
 * 媒體影片時間格式
 * */
@BindingAdapter("bind:mediaDuration")
fun TextView.bindMediaDuration(media: Media) {
    val durationFormat = context.getString(R.string.video_duration_format)
    val time = String.format(durationFormat, ((media.duration / 1000) % 3600) / 60, ((media.duration / 1000) % 60))
    text = time
}

/**
 * 媒體選取標題
 * */
@BindingAdapter("bind:mediaSelectTitle")
fun TextView.bindMediaSelectTitle(mediaList: List<Media>?) {
    if (mediaList == null) {
        isVisible = false
    } else {
        val count = mediaList.count { it.isChoice }
        if (count > 0) {
            isVisible = true
            val choiceFormat = context.getString(R.string.choice_count)
            val choice = String.format(choiceFormat, count.toString())
            text = choice
        } else {
            isVisible = false
        }
    }
}

/**
 * Notify
 * */
/**
 * 通知開關
 * */
@BindingAdapter("bind:isNotifyOpen")
fun ImageView.bindIsReadColor(isOpen: Boolean) {
    setImageResource(if (isOpen) R.drawable.ic_baseline_notifications_24_white else R.drawable.ic_baseline_notifications_off_24_white)
}