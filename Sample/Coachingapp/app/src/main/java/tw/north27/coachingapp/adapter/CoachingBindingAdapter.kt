package tw.north27.coachingapp.adapter

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import tw.north27.coachingapp.R

@BindingAdapter("bind:isReadColor")
fun View.bindIsReadColor(isRead: Boolean) {
    setBackgroundColor(if (isRead) resources.getColor(R.color.yellow_fef5e0) else Color.WHITE)
}

@BindingAdapter("bind:isNotifyOpen")
fun ImageView.bindIsReadColor(isOpen: Boolean) {
    setImageResource(if (isOpen) R.drawable.ic_baseline_notifications_24_white else R.drawable.ic_baseline_notifications_off_24_white)
}