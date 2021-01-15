package edu.yujie.socketex.`interface`

import edu.yujie.socketex.bean.IntentResult
import edu.yujie.socketex.bean.IntentSetting

interface ICrop {

    val REQUEST_CODE_CROP: Int
        get() = 1003

    fun createCropBuilder(setting: IntentSetting.CropSetting)

    fun onCropResult(result: IntentResult): IntentResult?
}