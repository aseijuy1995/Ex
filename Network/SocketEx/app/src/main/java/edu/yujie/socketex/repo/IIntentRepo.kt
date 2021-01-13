package edu.yujie.socketex.repo

import edu.yujie.socketex.bean.IntentSetting
import io.reactivex.rxjava3.core.Completable

interface IIntentRepo {

    fun startCamera(setting: IntentSetting): Completable

    fun startAlbum()

    fun startRecord()
}