package tw.north27.coachingapp.chat

import android.os.Bundle
import android.view.View
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import tw.north27.coachingapp.base.BaseViewBindingBottomSheetDialogFragment
import tw.north27.coachingapp.databinding.FragmentChatRoomMediaDialogBinding

class ChatRoomMediaDialogFragment : BaseViewBindingBottomSheetDialogFragment<FragmentChatRoomMediaDialogBinding>(FragmentChatRoomMediaDialogBinding::inflate) {

//    private val chatViewModel by sharedViewModel<ChatViewModel>()

    private val viewModel by viewModel<MediaViewModel>()

//    private lateinit var adapter: MediaListAdapter
//
//    private lateinit var mimeType: MimeType
//
//    private lateinit var setting: MediaSetting

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@ChatRoomMediaDialogFragment.viewModel

//            toolbar.setupWithNavController(navController)
        }

        viewModel.getMediaImages().subscribeWithRxLife {
            Timber.d("getMediaImages = $it")
        }


//        chatViewModel.cleanMediaList()
//
//        mimeType = arguments?.getSerializable("mimeType") as MimeType
//        when (mimeType) {
//            MimeType.ALL -> {
//                setting = MediaSetting()
//            }
//            MimeType.IMAGE -> {
//                setting = MediaSetting(
//                    mimeType = mimeType,
//                    isMultipleSelection = true
//                )
//            }
//            MimeType.VIDEO -> {
//                setting = MediaSetting(
//                    mimeType = mimeType,
//                    isMultipleSelection = false
//                )
//            }
//        }
//        adapter = MediaListAdapter(setting)
//        binding.rvMedia.adapter = adapter
//
//        adapter.itemClickRelay.subscribeWithLife {
//            findNavController().navigate(MediaListDialogFragmentDirections.actionFragmentMediaListDialogToFragmentMediaPreview(it, From.MEDIA_LIST))
//        }
//        adapter.itemSelectedRelay.subscribeWithLife {
//            chatViewModel.selectMedia(it)
//        }
//        val menuSend = binding.toolbar.menu.findItem(R.id.menu_send)
//        menuSend.clicks().subscribeWithLife {
//            chatViewModel.sendMediaList()
//            findNavController().navigateUp()
//        }
//
//        chatViewModel.getMediaAlbumItems(setting = setting).subscribeWithLife {
//            it.flatMap { it.mediaList }
//                .also { adapter.submitList(it) }
//        }
//
//        chatViewModel.toast.observe(viewLifecycleOwner) { if (it.trim().isNotEmpty()) findNavController().navigateUp() }
//
//        chatViewModel.selectMediaList.observe(viewLifecycleOwner) {
//            binding.toolbar.menu.findItem(R.id.menu_send).isVisible = (it.isNotEmpty())
//        }
    }


    private fun clickEvent() {

    }

}