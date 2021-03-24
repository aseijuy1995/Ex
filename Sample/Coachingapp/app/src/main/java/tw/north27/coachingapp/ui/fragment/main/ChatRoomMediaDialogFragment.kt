package tw.north27.coachingapp.ui.fragment.main

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import tw.north27.coachingapp.base.BaseViewBindingBottomSheetDialogFragment
import tw.north27.coachingapp.chat.MimeType
import tw.north27.coachingapp.databinding.FragmentChatRoomMediaDialogBinding
import tw.north27.coachingapp.media.MediaViewModel

class ChatRoomMediaDialogFragment : BaseViewBindingBottomSheetDialogFragment<FragmentChatRoomMediaDialogBinding>(FragmentChatRoomMediaDialogBinding::inflate) {

//    private val chatViewModel by sharedViewModel<ChatViewModel>()

    private val viewModel by viewModel<MediaViewModel>()

//    private lateinit var adapter: MediaListAdapter
//
//    private lateinit var mimeType: MimeType
//
//    private lateinit var setting: MediaSetting

//    private val mimeType: MimeType
//        get() = (arguments?.getSerializable("mimeType") as MimeType)

    private val args by navArgs<ChatRoomMediaDialogFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@ChatRoomMediaDialogFragment.viewModel
//            toolbar.setupWithNavController(navController)
        }

        when (args.mimeType) {
            MimeType.AUDIO -> {
                //audio
                viewModel.getMediaAudio().subscribeWithRxLife {
                    it.map {
                        Timber.d("getMediaAudio = ${it.albumName} = ${it.mediaList.size}")

                    }
                }
            }
            MimeType.IMAGES -> {
                //image
                viewModel.getMediaImages().subscribeWithRxLife {
                    it.map {
                        Timber.d("getMediaImages = ${it.albumName} = ${it.mediaList.size}")
                    }
                }
            }
            MimeType.VIDEO -> {
                //video
                viewModel.getMediaVideo().subscribeWithRxLife {
                    it.map {
                        Timber.d("getMediaVideo = ${it.albumName} = ${it.mediaList.size}")

                    }
                }
            }
        }


//        chatViewModel.cleanMediaList()
//
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

}