package tw.north27.coachingapp.adapter

import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import tw.north27.coachingapp.R
import tw.north27.coachingapp.model.Gender
import tw.north27.coachingapp.model.UserInfo
import java.text.SimpleDateFormat
import java.util.*

/**
 * UI是否顯示
 * @param userInfo >> 用戶資訊
 * */
@BindingAdapter("bind:gender")
fun TextView.bindGender(userInfo: UserInfo?) {
    @DrawableRes val imgRes: Int
    @StringRes val strRes: Int
    when (userInfo?.gender) {
        Gender.MALE -> {
            imgRes = R.drawable.shape_solid_blue_corners_radius_2
            strRes = R.string.male
        }
        Gender.FEMALE -> {
            imgRes = R.drawable.shape_solid_red_corners_radius_2
            strRes = R.string.female
        }
        else -> {
            imgRes = R.drawable.shape_solid_green_corners_radius_2
            strRes = R.string.not
        }
    }
    setBackgroundResource(imgRes)
    text = context.getString(strRes)
}

/**
 * 日期轉換
 * @param date >> 日期
 * */
@BindingAdapter("bind:dateFormat")
fun TextView.bindDate(date: Date?) {
    text = SimpleDateFormat("yyyy-MM-dd", Locale.TAIWAN).format(date)
}