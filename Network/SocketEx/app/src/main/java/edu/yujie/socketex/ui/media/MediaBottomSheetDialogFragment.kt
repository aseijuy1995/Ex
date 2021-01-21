package edu.yujie.socketex.ui.media

import android.os.Bundle
import android.view.View
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding4.view.clicks
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
        getArgument()
        initView()
        clickEvent()

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

        viewModel.selectMediaList.observe(viewLifecycleOwner) {
            binding.toolbar.menu.findItem(R.id.menu_send).isVisible = (it.size > 0)
        }
    }

    private fun getArgument() {
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

    private fun initView() {
        binding.toolbar.setupWithNavController(navController)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@MediaBottomSheetDialogFragment.viewModel
        }
        binding.rvMedia.adapter = adapter
        binding.toolbar.menu.findItem(R.id.menu_send).isVisible = false
    }

    private fun clickEvent() {
        adapter.itemClickRelay.subscribeWithLife {
            findNavController().navigate(MediaBottomSheetDialogFragmentDirections.actionFragmentMediaBottomSheetDialogToFragmentMediaDetail(it))
        }
        adapter.itemSelectedRelay.subscribeWithLife {
            viewModel.selectMedia(it)
        }
        val menuSend = binding.toolbar.menu.findItem(R.id.menu_send)
        menuSend
            .clicks()
            .subscribeWithLife {
                if (viewModel.isSelectMedia()) {
                    viewModel.sendMedia()
                    findNavController().navigateUp()
                } else
                    Snackbar.make(menuSend.actionView, "Please select ${mimeType}", Snackbar.LENGTH_SHORT).show()
            }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.cleanMediaStorage()
    }


}