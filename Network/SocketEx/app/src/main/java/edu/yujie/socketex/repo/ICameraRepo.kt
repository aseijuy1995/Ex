package edu.yujie.socketex.repo

import com.jakewharton.rxrelay3.BehaviorRelay
import edu.yujie.socketex.bean.IntentBuilder
import edu.yujie.socketex.bean.IntentResult
import edu.yujie.socketex.bean.IntentSetting

interface ICameraRepo {

    val REQUEST_CODE_CAPTURE: Int
        get() = 1001

    fun createCameraBuilder(setting: IntentSetting.CameraSetting): BehaviorRelay<IntentBuilder>

    fun onCameraResult(result: IntentResult): BehaviorRelay<IntentResult>

}