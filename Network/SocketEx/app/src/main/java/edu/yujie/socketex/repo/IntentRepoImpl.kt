package edu.yujie.socketex.repo

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import com.jakewharton.rxrelay3.BehaviorRelay
import edu.yujie.socketex.bean.IntentBuilder
import edu.yujie.socketex.bean.IntentResult
import edu.yujie.socketex.bean.IntentSetting
import edu.yujie.socketex.util.createFile
import edu.yujie.socketex.util.getContentUri

class IntentRepoImpl : IIntentRepo() {

    private var mUrisRelay: BehaviorRelay<List<Uri>> = BehaviorRelay.create<List<Uri>>()

    private val mBuilderRelay = BehaviorRelay.create<IntentBuilder>()

    private val mResultRelay = BehaviorRelay.create<IntentResult>()

    override fun createCameraBuilder(setting: IntentSetting.CameraSetting): BehaviorRelay<IntentBuilder> {
        val dispose = setting.file.createFile()
            .flatMap { setting.context.getContentUri(it) }
            .map {
                mUrisRelay.accept(listOf(it))
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
                    putExtra(MediaStore.EXTRA_OUTPUT, it)
                }
                mBuilderRelay.accept(IntentBuilder(intent = intent, requestCode = REQUEST_CODE_CAPTURE))
            }.subscribe()
//        dispose.dispose()
        return mBuilderRelay
    }

    override fun onCameraResult(result: IntentResult): BehaviorRelay<IntentResult> {
        if (result.requestCode == REQUEST_CODE_CAPTURE) {
            if (result.resultCode == Activity.RESULT_OK) {
                result.uris = mUrisRelay.value
                mResultRelay.accept(IntentResult.IntentResultSuccess(result))
            } else {
                mResultRelay.accept(IntentResult.IntentResultFailed(result))
            }
        }
        return mResultRelay
    }

    override fun buildAlbum(setting: IntentSetting) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            type = "image/*"
        }
//        setting.doStart?.invoke(IntentBuilder(intent, REQUEST_CODE_ALBUM))
    }

    override fun onAlbumResult(result: IntentResult): BehaviorRelay<IntentResult> {
        if (result.requestCode == REQUEST_CODE_ALBUM) {
            if (result.resultCode == Activity.RESULT_OK) {
                val albumUriList = mutableListOf<Uri>()
                result.data?.clipData?.let {
                    for (i in 0 until it.itemCount) {
                        val albumUri = it.getItemAt(i).uri
                        albumUriList.add(albumUri)
                    }
                }
                result.data?.data?.let { albumUri ->
                    albumUriList.add(albumUri)
                }
                result.uris = albumUriList
                mResultRelay.accept(IntentResult.IntentResultSuccess(result))
            } else {
                mResultRelay.accept(IntentResult.IntentResultFailed(result))
            }
        }
        return mResultRelay
    }

}