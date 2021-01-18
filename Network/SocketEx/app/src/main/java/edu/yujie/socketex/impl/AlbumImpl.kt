package edu.yujie.socketex.impl

import android.app.Activity
import android.content.Intent
import android.net.Uri
import edu.yujie.socketex.bean.IntentBuilder
import edu.yujie.socketex.bean.IntentResult
import edu.yujie.socketex.bean.IntentSetting
import edu.yujie.socketex.inter.IAlbum
import io.reactivex.rxjava3.core.Observable

class AlbumImpl : IAlbum {

    override fun createAlbumBuilder(setting: IntentSetting.AlbumSetting?): Observable<IntentBuilder> {
        return Observable.create<Intent> {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                type = "image/*"
            }
            it.onNext(intent)
        }.map {
            IntentBuilder(it, REQUEST_CODE_ALBUM)
        }
    }

    override fun onAlbumResult(result: IntentResult): Observable<IntentResult> {
        return Observable.just(result)
            .map { result ->
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