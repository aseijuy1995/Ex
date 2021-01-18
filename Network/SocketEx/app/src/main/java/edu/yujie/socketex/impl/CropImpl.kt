package edu.yujie.socketex.impl

import android.app.Activity
import android.content.Intent
import android.net.Uri
import edu.yujie.socketex.bean.IntentBuilder
import edu.yujie.socketex.bean.IntentResult
import edu.yujie.socketex.bean.IntentSetting
import edu.yujie.socketex.inter.ICrop
import io.reactivex.rxjava3.core.Observable

class CropImpl : ICrop {
    private var uri: Uri? = null

    override fun createCropBuilder(setting: IntentSetting.CropSetting): Observable<IntentBuilder> {
        return Observable.create<Intent> {
            val intent = Intent("com.android.camera.action.CROP").apply {
                setDataAndType(setting.uri, "image/*")
                putExtra("crop", "true")
                putExtra("aspectX", setting.aspectX)
                putExtra("aspectY", setting.aspectY)
                putExtra("outputX", setting.outputX)
                putExtra("outputY", setting.outputY)
                putExtra("output", setting.uri)
                putExtra("scale", setting.scale)
                putExtra("return-data", setting.return_data)
                putExtra("outputFormat", setting.format)
            }
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            uri = setting.uri
            it.onNext(intent)
        }.map {
            IntentBuilder(intent = it, requestCode = REQUEST_CODE_CROP)
        }
    }

    override fun onCropResult(result: IntentResult): Observable<IntentResult> {
        return Observable.just(result)
            .map { result ->
                if (result.requestCode == REQUEST_CODE_CROP) {
                    if (result.resultCode == Activity.RESULT_OK) {
                        result.uris = listOf(uri!!)
                        IntentResult.IntentResultSuccess(result)
                    } else {
                        IntentResult.IntentResultFailed(result)
                    }
                } else {
                    IntentResult.IntentResultOther(result)
                }
            }
    }
}