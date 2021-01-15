package edu.yujie.socketex.`interface`

import edu.yujie.socketex.bean.IntentResult
import edu.yujie.socketex.bean.IntentSetting

interface IAlbum {

    val REQUEST_CODE_ALBUM: Int
        get() = 1002

    fun createAlbumBuilder(setting: IntentSetting.AlbumSetting)

    fun onAlbumResult(result: IntentResult): IntentResult?

}