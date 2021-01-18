package edu.yujie.socketex.inter

import edu.yujie.socketex.bean.IntentBuilder
import edu.yujie.socketex.bean.IntentResult
import edu.yujie.socketex.bean.IntentSetting
import io.reactivex.rxjava3.core.Observable

interface IAlbum {

    val REQUEST_CODE_ALBUM: Int
        get() = 1002

    fun createAlbumBuilder(setting: IntentSetting.AlbumSetting?): Observable<IntentBuilder>

    fun onAlbumResult(result: IntentResult): Observable<IntentResult>

}