package tw.north27.coachingapp.ui.fragment.main

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import tw.north27.coachingapp.R
import tw.north27.coachingapp.base.BaseViewBindingBottomSheetDialogFragment
import tw.north27.coachingapp.chat.MediaSetting
import tw.north27.coachingapp.chat.MimeType
import tw.north27.coachingapp.databinding.FragmentChatRoomMediaDialogBinding
import tw.north27.coachingapp.media.*

class ChatRoomMediaDialogFragment : BaseViewBindingBottomSheetDialogFragment<FragmentChatRoomMediaDialogBinding>(FragmentChatRoomMediaDialogBinding::inflate) {

//    private val chatViewModel by sharedViewModel<ChatViewModel>()

    private val viewModel by viewModel<MediaViewModel>()

//    private lateinit var adapter: MediaListAdapter
//
//    private lateinit var mimeType: MimeType
//
//    private lateinit var setting: MediaSetting

    private val args by navArgs<ChatRoomMediaDialogFragmentArgs>()

    private lateinit var adapter: ChatRoomMediaListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@ChatRoomMediaDialogFragment.viewModel
//            toolbar.setupWithNavController(navController)
        }

        when (args.mimeType) {
            MimeType.AUDIO -> {
                val setting = MediaSetting(
                    mimeType = MimeType.AUDIO,
                    isMultipleChoice = true,
                    audioMinDuration = 0,
                    audioMaxDuration = 60000//1m
                )
                adapter = ChatRoomMediaListAdapter(args.mimeType, setting)
                binding.rvMedia.apply {
                    addItemDecoration(DividerItemDecoration(cxt, LinearLayoutManager.VERTICAL).apply {
                        setDrawable(ContextCompat.getDrawable(cxt, R.drawable.shape_size_height_1_solid_gray) ?: return)
                    })
                    layoutManager = LinearLayoutManager(cxt)
                    adapter = this@ChatRoomMediaDialogFragment.adapter
                }
                //audio
                viewModel.getMediaAudio(setting).subscribeWithRxLife {
                    val mediaList = it.find { it.albumName == MEDIA_ALBUM_AUDIO }?.mediaList
                    adapter.submitList(mediaList)
                }
            }
            MimeType.IMAGES -> {
                val setting = MediaSetting(
                    mimeType = MimeType.IMAGES,
                    isMultipleChoice = true
                )
                adapter = ChatRoomMediaListAdapter(args.mimeType, setting)
                binding.rvMedia.apply {
                    layoutManager = GridLayoutManager(cxt, 3)
                    adapter = this@ChatRoomMediaDialogFragment.adapter
                }
                //image
                viewModel.getMediaImages(setting).subscribeWithRxLife {
                    val mediaList = it.find { it.albumName == MEDIA_ALBUM_IMAGES }?.mediaList
                    adapter.submitList(mediaList)

                }
            }
            MimeType.VIDEO -> {
                val setting = MediaSetting(
                    mimeType = MimeType.VIDEO,
                    isMultipleChoice = true,
                    videoMinDuration = 0,//m
                    videoMaxDuration = 60000//1m
                )
                adapter = ChatRoomMediaListAdapter(args.mimeType, setting)
                binding.rvMedia.apply {
                    layoutManager = GridLayoutManager(cxt, 3)
                    adapter = this@ChatRoomMediaDialogFragment.adapter
                }
                //video
                viewModel.getMediaVideo(setting).subscribeWithRxLife {
                    val mediaList = it.find { it.albumName == MEDIA_ALBUM_VIDEO }?.mediaList
                    adapter.submitList(mediaList)

                }
            }
        }

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