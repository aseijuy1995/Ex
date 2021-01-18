package edu.yujie.socketex.inter

import edu.yujie.socketex.bean.IntentBuilder
import edu.yujie.socketex.bean.IntentResult
import edu.yujie.socketex.bean.IntentSetting
import io.reactivex.rxjava3.core.Observable

interface ICrop {

    val REQUEST_CODE_CROP: Int
        get() = 1003

    fun createCropBuilder(setting: IntentSetting.CropSetting): Observable<IntentBuilder>

    fun onCropResult(result: IntentResult): Observable<IntentResult>
}