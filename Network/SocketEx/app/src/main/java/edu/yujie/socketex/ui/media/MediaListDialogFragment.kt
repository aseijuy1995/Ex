package edu.yujie.socketex.ui.media

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.jakewharton.rxbinding4.view.clicks
import edu.yujie.socketex.R
import edu.yujie.socketex.adapter.MediaListAdapter
import edu.yujie.socketex.finish.base.fragment.BaseDataBindingBottomSheetDialogFragment
import edu.yujie.socketex.bean.MediaSetting
import edu.yujie.socketex.bean.MimeType
import edu.yujie.socketex.databinding.FragmentMediaListDialogBinding
import edu.yujie.socketex.listener.From
import edu.yujie.socketex.vm.MediaViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MediaListDialogFragment : BaseDataBindingBottomSheetDialogFragment<FragmentMediaListDialogBinding>(R.layout.fragment_media_list_dialog) {

    private val viewModel by sharedViewModel<MediaViewModel>()

    private val adapter = MediaListAdapter()

    private lateinit var mimeType: MimeType

    private lateinit var setting: MediaSetting

    private lateinit var navHostFrag: NavHostFragment
    
    private lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        navHostFrag = requireActivity().supportFragmentManager.findFragmentById(R.id.frag_container_view) as NavHostFragment
        navController = navHostFrag.navController
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        getArgument()
        clickEvent()
        println("MediaDialogFragment:onViewCreated")

        viewModel.getMediaAlbumItems(setting = setting).subscribeWithLife {
            val mediaList = it.flatMap { it.mediaList }
            adapter.submitList(mediaList)
        }

        viewModel.toast.observe(viewLifecycleOwner) { if (it.trim().isNotEmpty()) findNavController().navigateUp() }

        viewModel.selectMediaList.observe(viewLifecycleOwner) {
            binding.toolbar.menu.findItem(R.id.menu_send).isVisible = (it.isNotEmpty())
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
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@MediaListDialogFragment.viewModel
            toolbar.setupWithNavController(navController)
            rvMedia.adapter = adapter

            toolbar.menu.findItem(R.id.menu_send).isVisible = false
        }
        viewModel.cleanSelectMediaList()
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
            viewModel.sendSelectMediaList()
            findNavController().navigateUp()
        }
    }

}