package tw.north27.coachingapp.model.transfer

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * 發送訊息模式
 * @param CAMERA >> 拍照
 * @param ALBUM >> 照片
 * @param RECORDING >> 錄音
 * @param AUDIO >> 音訊
 * @param VIDEO >> 錄影
 * @param FILM >> 影片
 * */
@Parcelize
enum class SendMode : Parcelable {
    CAMERA,
    ALBUM,
    RECORDING,
    AUDIO,
    VIDEO,
    FILM
}