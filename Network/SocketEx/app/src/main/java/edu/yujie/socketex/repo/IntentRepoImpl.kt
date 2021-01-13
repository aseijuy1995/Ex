package edu.yujie.socketex.repo

import edu.yujie.socketex.bean.IntentSetting
import edu.yujie.socketex.util.createFile
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers

class IntentRepoImpl : IIntentRepo {

    override fun startCamera(setting: IntentSetting): Completable = Completable.fromAction {
        setting.file.createFile()
            .subscribeOn(Schedulers.io())
    }

//        captureUri = context.getContentUri(setting)
//        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
//            putExtra(MediaStore.EXTRA_OUTPUT, captureUri)
//        }


    override fun startAlbum() {
        TODO("Not yet implemented")
    }

    override fun startRecord() {
        TODO("Not yet implemented")
    }
}