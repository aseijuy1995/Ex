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

    private var mCameraUri: Uri? = null

    private val mResultBehaviorRelay = BehaviorRelay.create<IntentResult>()

    override fun buildCamera(setting: IntentSetting) {
        val file = setting.file!!.createFile()
        val uri = setting.context!!.getContentUri(file)
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            putExtra(MediaStore.EXTRA_OUTPUT, uri)
        }
        mCameraUri = uri
        setting.doStart?.invoke(IntentBuilder(intent, REQUEST_CODE_CAPTURE))
    }

    override fun onCameraResult(result: IntentResult): BehaviorRelay<IntentResult> {
        if (result.requestCode == REQUEST_CODE_CAPTURE) {
            if (result.resultCode == Activity.RESULT_OK) {
                mCameraUri?.let { result.uris = listOf(it) }
                mResultBehaviorRelay.accept(IntentResult.IntentResultSuccess(result))
            } else {
                mResultBehaviorRelay.accept(IntentResult.IntentResultFailed(result))
            }
        }
        return mResultBehaviorRelay
    }

    override fun buildAlbum(setting: IntentSetting) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            type = "image/*"
        }
        setting.doStart?.invoke(IntentBuilder(intent, REQUEST_CODE_ALBUM))
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
                mResultBehaviorRelay.accept(IntentResult.IntentResultSuccess(result))
            } else {
                mResultBehaviorRelay.accept(IntentResult.IntentResultFailed(result))
            }
        }
        return mResultBehaviorRelay
    }

}