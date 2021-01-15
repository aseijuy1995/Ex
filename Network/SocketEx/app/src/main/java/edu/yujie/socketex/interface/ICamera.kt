package edu.yujie.socketex.`interface`

import edu.yujie.socketex.bean.IntentResult
import edu.yujie.socketex.bean.IntentSetting

interface ICamera {

    val REQUEST_CODE_CAPTURE: Int
        get() = 1001

    fun createCameraBuilder(setting: IntentSetting.CameraSetting)

    fun onCameraResult(result: IntentResult): IntentResult?

}