package edu.yujie.socketex.ui.media

import android.os.Bundle
import android.view.View
import edu.yujie.socketex.R
import edu.yujie.socketex.adapter.MediaListAdapter
import edu.yujie.socketex.base.BaseBottomSheetDialogFragment
import edu.yujie.socketex.bean.MediaAlbumItem
import edu.yujie.socketex.bean.MediaSetting
import edu.yujie.socketex.databinding.FragmentMediaBinding
import edu.yujie.socketex.vm.MediaViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.androidx.viewmodel.ext.android.viewModel

class MediaFragment : BaseBottomSheetDialogFragment<FragmentMediaBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_media

    private val viewModel by viewModel<MediaViewModel>()

    private val adapter = MediaListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvMedia.adapter = adapter

        viewModel.getMediaItems()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<List<MediaAlbumItem>> {
                override fun onSubscribe(d: Disposable?) {
                    println("$TAG onSubscribe")
                }

                override fun onNext(t: List<MediaAlbumItem>?) {
                    println("$TAG onNext: $t")
                }

                override fun onError(e: Throwable?) {
                    e?.printStackTrace()
                    println("$TAG onError: ${e?.message}")
                }

                override fun onComplete() {
                    println("$TAG onComplete")
                }

            })

    }


}