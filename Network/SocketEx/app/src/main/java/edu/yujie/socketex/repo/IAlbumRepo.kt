package edu.yujie.socketex.repo

import com.jakewharton.rxrelay3.BehaviorRelay
import edu.yujie.socketex.bean.IntentResult
import edu.yujie.socketex.bean.IntentSetting

interface IAlbumRepo {

    val REQUEST_CODE_ALBUM: Int
        get() = 1002

    fun buildAlbum(setting: IntentSetting)

    fun onAlbumResult(result: IntentResult): BehaviorRelay<IntentResult>

}