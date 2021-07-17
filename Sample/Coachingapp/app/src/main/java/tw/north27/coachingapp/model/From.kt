package tw.north27.coachingapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @param Specify >> 指定
 * @param Pair >> 配對
 * */
@Parcelize
enum class From(val code: Int) : Parcelable {
    Specify(1),
    Pair(2)
}