package edu.yujie.socketex.impl

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import com.jakewharton.rxrelay3.BehaviorRelay
import edu.yujie.socketex.`interface`.IIntentRepo
import edu.yujie.socketex.bean.IntentBuilder
import edu.yujie.socketex.bean.IntentResult
import edu.yujie.socketex.bean.IntentSetting
import edu.yujie.socketex.util.createFile
import edu.yujie.socketex.util.getContentUri


class IntentRepoImpl : IIntentRepo() {

    private var mUrisRelay: BehaviorRelay<List<Uri>> = BehaviorRelay.create<List<Uri>>()

    //camera
    override fun createCameraBuilder(setting: IntentSetting.CameraSetting) {
        val file = setting.file.createFile()
        val uri = setting.context.getContentUri(file)
        mUrisRelay.accept(listOf(uri))
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            putExtra(MediaStore.EXTRA_OUTPUT, uri)
        }
        setting.doStart(IntentBuilder(intent = intent, requestCode = REQUEST_CODE_CAPTURE))
    }

    override fun onCameraResult(result: IntentResult): IntentResult? {
        return if (result.requestCode == REQUEST_CODE_CAPTURE) {
            if (result.resultCode == Activity.RESULT_OK) {
                result.uris = mUrisRelay.value
                IntentResult.IntentResultSuccess(result)
            } else {
                IntentResult.IntentResultFailed(result)
            }
        } else {
            null
        }
    }

    //crop
    override fun createCropBuilder(setting: IntentSetting.CropSetting) {
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
        setting.doStart(IntentBuilder(intent = intent, requestCode = REQUEST_CODE_CROP))
    }

    override fun onCropResult(result: IntentResult): IntentResult? {
        return if (result.requestCode == REQUEST_CODE_CROP) {
            if (result.resultCode == Activity.RESULT_OK) {
                result.uris = mUrisRelay.value
                IntentResult.IntentResultSuccess(result)
            } else {
                IntentResult.IntentResultFailed(result)
            }
        } else {
            null
        }
    }

    //album
    override fun createAlbumBuilder(setting: IntentSetting.AlbumSetting) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            type = "image/*"
        }
        setting.doStart(IntentBuilder(intent, REQUEST_CODE_ALBUM))
    }

    override fun onAlbumResult(result: IntentResult): IntentResult? {
        return if (result.requestCode == REQUEST_CODE_ALBUM) {
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
                IntentResult.IntentResultSuccess(result)
            } else {
                IntentResult.IntentResultFailed(result)
            }
        } else {
            null
        }
    }

}