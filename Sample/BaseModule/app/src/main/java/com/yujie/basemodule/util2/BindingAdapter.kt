package tw.north27.coachingapp.util2

import android.graphics.Color
import android.graphics.Typeface
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.yujie.utilmodule.pref.getAccount
import com.yujie.utilmodule.pref.userPref
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import tw.north27.coachingapp.R
import tw.north27.coachingapp.media.mediaStore.Media
import tw.north27.coachingapp.model.result.ChatInfo
import tw.north27.coachingapp.model.result.ChatRead

/**
 * 圖片高斯模糊
 * @param drawableResId >> Resource資源（bind:imgBlurRes）
 * @param radius >> （bind:imgBlurRadius）
 * @param sampling >> 採樣(模糊)（bind:imgBlurSampling）
 * */
@BindingAdapter(value = ["bind:imgBlurRes", "bind:imgBlurRadius", "bind:imgBlurSampling"])
fun ImageView.bindImgBlurRes(
    @DrawableRes drawableResId: Int,
    radius: Int = 15,
    sampling: Int = 3
) {
    Glide.with(this)
        .load(drawableResId)
        .apply(RequestOptions.bitmapTransform(BlurTransformation(radius, sampling)))
        .into(this)
}

/**
 * 圖片
 * @param url >> 圖片url
 * @param placeholderResId >> 預設圖片Drawable（bind:imgPlaceholderRes）
 * */
@BindingAdapter("bind:imgUrl2", )
//"bind:imgPlaceholderRes"
fun ImageView.bindImgUrl2(
    url: String?,
//    @DrawableRes placeholderResId: Int
) {
//    Glide.with(this).load(url).placeholder(placeholderResId).centerCrop().into(this)
    Glide.with(this).load(url).placeholder(R.drawable.ic_baseline_person_24_gray).centerCrop().into(this)
}

//------------------------------------------------------------------------------------------------------------------------------

/**
 * ChatRoom
 * */
@BindingAdapter("bind:chatRoomTitleText")
fun TextView.bindChatRoomTitleText(chat: ChatInfo) {
    val account = runBlocking { context.userPref.getAccount().first() }
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
fun ImageView.bindImgUrl(url: String?) {
    Glide.with(this).load(url).placeholder(R.drawable.ic_pencil_logo).centerCrop().into(this)
}

@BindingAdapter("bind:imgRes")
fun ImageView.bindImgRes(resId: Int) {
    setImageResource(resId)
}

@BindingAdapter("bind:imgByteArray")
fun ImageView.bindImgByteArray(byteArray: ByteArray) {
    Glide.with(context).load(byteArray).placeholder(R.drawable.ic_pencil_logo).into(this)
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
    val account = runBlocking { context.userPref.getAccount().first() }
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
    val account = runBlocking { context.userPref.getAccount().first() }

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
//    val durationFormat = context.getString(R.string.video_duration_format)
//    val time = String.format(durationFormat, ((media.duration / 1000) % 3600) / 60, ((media.duration / 1000) % 60))
    val time = "${((media.duration / 1000) % 3600) / 60}:${((media.duration / 1000) % 60)}}"
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
    setImageResource(if (isOpen) R.drawable.ic_twotone_notifications_24_white else R.drawable.ic_baseline_notifications_off_24_white)
}