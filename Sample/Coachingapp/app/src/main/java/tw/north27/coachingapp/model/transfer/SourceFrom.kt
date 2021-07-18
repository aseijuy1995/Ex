package tw.north27.coachingapp.model.transfer

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * 傳輸數據來源
 * @param Specify >> 指定
 * @param Pair >> 配對
 * */
@Parcelize
enum class SourceFrom(val code: Int) : Parcelable {
    Specify(1),
    Pair(2)
}