package edu.yujie.socketex.impl

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import edu.yujie.socketex.bean.IntentBuilder
import edu.yujie.socketex.bean.IntentResult
import edu.yujie.socketex.bean.IntentSetting
import edu.yujie.socketex.inter.ICamera
import edu.yujie.socketex.util.createFile
import edu.yujie.socketex.util.getContentUri
import io.reactivex.rxjava3.core.Observable

class CameraImpl : ICamera {
    private var uri: Uri? = null

    override fun createCameraBuilder(setting: IntentSetting.CameraSetting): Observable<IntentBuilder> {
        return Observable.just(setting.filePath)
            .map { it?.createFile(setting.fileName)!! }
            .map { setting.context.getContentUri(it) }
            .map {
                uri = it
                Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
                    putExtra(MediaStore.EXTRA_OUTPUT, it)
                }
            }.map { IntentBuilder(intent = it, requestCode = REQUEST_CODE_CAPTURE) }
    }

    override fun onCameraResult(result: IntentResult): Observable<IntentResult> {
        return Observable.just(result)
            .map { result ->
                if (result.requestCode == REQUEST_CODE_CAPTURE) {
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