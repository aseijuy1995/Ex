package edu.yujie.socketex.ui.media

import android.os.Bundle
import android.view.View
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.jakewharton.rxbinding4.view.clicks
import edu.yujie.socketex.R
import edu.yujie.socketex.adapter.MediaListAdapter
import edu.yujie.socketex.bean.MediaSetting
import edu.yujie.socketex.bean.MimeType
import edu.yujie.socketex.databinding.FragmentMediaListDialogBinding
import edu.yujie.socketex.finish.base.fragment.BaseDataBindingBottomSheetDialogFragment
import edu.yujie.socketex.listener.From
import edu.yujie.socketex.vm.MediaViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MediaListDialogFragment : BaseDataBindingBottomSheetDialogFragment<FragmentMediaListDialogBinding>(R.layout.fragment_media_list_dialog) {

    private val viewModel by sharedViewModel<MediaViewModel>()

    private val adapter = MediaListAdapter()

    private lateinit var mimeType: MimeType

    private lateinit var setting: MediaSetting

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        getArgument()
        clickEvent()

        viewModel.getMediaAlbumItems(setting = setting).subscribeWithLife {
            val mediaList = it.flatMap { it.mediaList }
            adapter.submitList(mediaList)
        }

        viewModel.toast.observe(viewLifecycleOwner) { if (it.trim().isNotEmpty()) findNavController().navigateUp() }

        viewModel.selectMediaList.observe(viewLifecycleOwner) {
            binding.toolbar.menu.findItem(R.id.menu_send).isVisible = (it.isNotEmpty())
        }
    }

    private fun initView() {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@MediaListDialogFragment.viewModel
            toolbar.setupWithNavController(navController)
            rvMedia.adapter = adapter
        }
        viewModel.cleanSelectMediaList()
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
                )
            }
            MimeType.VIDEO -> {
                setting = MediaSetting(
                    mimeType = mimeType,
                    isMultipleSelection = true
                )
            }
        }
    }

    private fun clickEvent() {
        adapter.itemClickRelay.subscribeWithLife {
            findNavController().navigate(MediaListDialogFragmentDirections.actionFragmentMediaListDialogToFragmentMediaPreview(it, From.MEDIA_LIST))
        }
        adapter.itemSelectedRelay.subscribeWithLife {
            viewModel.selectMedia(it)
        }
        val menuSend = binding.toolbar.menu.findItem(R.id.menu_send)
        menuSend.clicks().subscribeWithLife {
            viewModel.sendMediaList()
            findNavController().navigateUp()
        }
    }

}