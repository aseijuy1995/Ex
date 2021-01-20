package edu.yujie.socketex.ui.media

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import edu.yujie.socketex.R
import edu.yujie.socketex.adapter.MediaListAdapter
import edu.yujie.socketex.base.BaseBottomSheetDialogFragment
import edu.yujie.socketex.bean.MediaSetting
import edu.yujie.socketex.bean.MimeType
import edu.yujie.socketex.databinding.FragmentMediaBottomSheetDialogBinding
import edu.yujie.socketex.vm.MediaViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MediaBottomSheetDialogFragment : BaseBottomSheetDialogFragment<FragmentMediaBottomSheetDialogBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_media_bottom_sheet_dialog

    private val viewModel by sharedViewModel<MediaViewModel>()

    private val adapter = MediaListAdapter()

    private lateinit var mimeType: MimeType

    private lateinit var setting: MediaSetting

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initArguments()
        clickEvent()
        binding.rvMedia.adapter = adapter

        viewModel.getMediaAlbumItems(setting = setting)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWithLife {
                val mediaList = it.flatMap { it.mediaList }
                adapter.submitList(mediaList)
            }

        viewModel.toastRelay.subscribeWithLife {
            if (it.trim().isNotEmpty())
                findNavController().navigateUp()
        }

    }

    private fun initArguments() {
        mimeType = arguments?.getSerializable("mimeType") as MimeType
        when (mimeType) {
            MimeType.ALL -> {
                setting = MediaSetting()
            }
            MimeType.IMAGE -> {
                setting = MediaSetting(
                    mimeType = mimeType,
                    isMultipleSelection = true
                    //                    imageMaxSize = 100
                )
            }
            MimeType.VIDEO -> {
                setting = MediaSetting(
                    mimeType = mimeType,
                    isMultipleSelection = true,
                    maxSelectionCount = 3
                    //                    videoMaxDuration =0,
                    //                    videoMinDuration = 0
                )
            }
        }
    }

    private fun clickEvent() {
        adapter.itemClickRelay.subscribeWithLife {
            if (it.mimeType.startsWith(MimeType.IMAGE.toString())) {


            } else if (it.mimeType.startsWith(MimeType.VIDEO.toString())) {
                findNavController().navigate(MediaBottomSheetDialogFragmentDirections.actionFragmentMediaBottomSheetDialogToFragmentMediaDetail(it))
            }
        }
    }

}