package edu.yujie.socketex.repo

import com.jakewharton.rxrelay3.BehaviorRelay
import edu.yujie.socketex.bean.IntentResult
import edu.yujie.socketex.bean.IntentSetting

interface ICameraRepo {

    val REQUEST_CODE_CAPTURE: Int
        get() = 1001

    fun buildCamera(setting: IntentSetting)

    fun onCameraResult(result: IntentResult): BehaviorRelay<IntentResult>

}