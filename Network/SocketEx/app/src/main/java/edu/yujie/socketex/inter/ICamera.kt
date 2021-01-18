package edu.yujie.socketex.inter

import edu.yujie.socketex.bean.IntentBuilder
import edu.yujie.socketex.bean.IntentResult
import edu.yujie.socketex.bean.IntentSetting
import io.reactivex.rxjava3.core.Observable

interface ICamera {

    val REQUEST_CODE_CAPTURE: Int
        get() = 1001

    fun createCameraBuilder(setting: IntentSetting.CameraSetting):Observable<IntentBuilder>

    fun onCameraResult(result: IntentResult): Observable<IntentResult>

}