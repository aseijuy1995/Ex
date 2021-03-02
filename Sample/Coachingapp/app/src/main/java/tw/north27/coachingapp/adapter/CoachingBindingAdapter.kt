package tw.north27.coachingapp.adapter

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import tw.north27.coachingapp.R

@BindingAdapter("bind:isReadIcon")
fun ImageView.bindIsReadIcon(isRead: Boolean) {
    setImageResource(if (isRead) R.drawable.ic_baseline_mark_chat_read_24_green else R.drawable.ic_baseline_mark_chat_unread_24_orange)
}

@BindingAdapter("bind:isReadColor")
fun View.bindIsReadColor(isRead: Boolean) {
    setBackgroundColor(if (isRead) resources.getColor(R.color.yellow_fef5e0) else Color.WHITE)
}