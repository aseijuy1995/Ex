package tw.north27.coachingapp.chat

import android.os.Bundle
import android.view.View
import edu.yujie.socketex.adapter.MediaListAdapter
import edu.yujie.socketex.listener.From
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import tw.north27.coachingapp.R
import tw.north27.coachingapp.base.BaseViewBindingBottomSheetDialogFragment
import tw.north27.coachingapp.databinding.FragmentChatRoomMediaDialogBinding

class ChatRoomMediaDialogFragment : BaseViewBindingBottomSheetDialogFragment<FragmentChatRoomMediaDialogBinding>(FragmentChatRoomMediaDialogBinding::inflate) {

    private val chatViewModel by sharedViewModel<ChatViewModel>()

    private val mediaViewModel by viewModel<MediaViewModel>()

    private lateinit var adapter: MediaListAdapter

    private lateinit var mimeType: MimeType

    private lateinit var setting: MediaSetting

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            mediaViewModel = this@ChatRoomMediaDialogFragment.chatViewModel
            mediaViewModel = this@ChatRoomMediaDialogFragment.chatViewModel

//            toolbar.setupWithNavController(navController)
        }
        chatViewModel.cleanMediaList()

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
                    isMultipleSelection = false
                )
            }
        }
        adapter = MediaListAdapter(setting)
        binding.rvMedia.adapter = adapter




        adapter.itemClickRelay.subscribeWithLife {
            findNavController().navigate(MediaListDialogFragmentDirections.actionFragmentMediaListDialogToFragmentMediaPreview(it, From.MEDIA_LIST))
        }
        adapter.itemSelectedRelay.subscribeWithLife {
            chatViewModel.selectMedia(it)
        }
        val menuSend = binding.toolbar.menu.findItem(R.id.menu_send)
        menuSend.clicks().subscribeWithLife {
            chatViewModel.sendMediaList()
            findNavController().navigateUp()
        }

        chatViewModel.getMediaAlbumItems(setting = setting).subscribeWithLife {
            it.flatMap { it.mediaList }
                .also { adapter.submitList(it) }
        }

        chatViewModel.toast.observe(viewLifecycleOwner) { if (it.trim().isNotEmpty()) findNavController().navigateUp() }

        chatViewModel.selectMediaList.observe(viewLifecycleOwner) {
            binding.toolbar.menu.findItem(R.id.menu_send).isVisible = (it.isNotEmpty())
        }
    }


    private fun clickEvent() {

    }

}