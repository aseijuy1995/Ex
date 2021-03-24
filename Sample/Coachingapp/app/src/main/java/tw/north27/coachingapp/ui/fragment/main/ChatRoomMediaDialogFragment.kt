package tw.north27.coachingapp.ui.fragment.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding4.view.clicks
import org.koin.androidx.viewmodel.ext.android.viewModel
import tw.north27.coachingapp.R
import tw.north27.coachingapp.base.BaseBottomSheetDialogFragment
import tw.north27.coachingapp.chat.MediaSetting
import tw.north27.coachingapp.chat.MimeType
import tw.north27.coachingapp.databinding.FragmentChatRoomMediaDialogBinding
import tw.north27.coachingapp.ext.dataBinding
import tw.north27.coachingapp.media.*

class ChatRoomMediaDialogFragment : BaseBottomSheetDialogFragment() {

    protected lateinit var cxt: Context

    private val binding by dataBinding<FragmentChatRoomMediaDialogBinding>(R.layout.fragment_chat_room_media_dialog)

    private val viewModel by viewModel<MediaViewModel>()

    private val args by navArgs<ChatRoomMediaDialogFragmentArgs>()

    private lateinit var adapter: ChatRoomMediaListAdapter

    companion object {
        val REQUEST_KEY_MEDIA = "REQUEST_KEY_MEDIA"

        val KEY_MEDIA = "KEY_MEDIA"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        cxt = requireContext()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@ChatRoomMediaDialogFragment.viewModel

        }
        binding.toolbarMedia.setupWithNavController(findNavController())

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
                    viewModel.setMediaList(mediaList)
                }
                //
                adapter.itemClickRelay.subscribeWithRxLife {
                    /**
                     * 播放音訊
                     * */
                }
                adapter.itemSelectRelay.subscribeWithRxLife {
                    viewModel.setMedia(it.third)
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
                    viewModel.setMediaList(mediaList)
                }
                adapter.itemClickRelay.subscribeWithRxLife {
                    /**
                     * 放大
                     * */
//                    findNavController().navigate(MediaListDialogFragmentDirections.actionFragmentMediaListDialogToFragmentMediaPreview(it, From.MEDIA_LIST))
                }
                adapter.itemSelectRelay.subscribeWithRxLife {
                    viewModel.setMedia(it.third)
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
                    viewModel.setMediaList(mediaList)
                }
                adapter.itemClickRelay.subscribeWithRxLife {
                    /**
                     * 播放影片
                     * */
//                    findNavController().navigate(MediaListDialogFragmentDirections.actionFragmentMediaListDialogToFragmentMediaPreview(it, From.MEDIA_LIST))
                }
                adapter.itemSelectRelay.subscribeWithRxLife {
                    viewModel.setMedia(it.third)
                }
            }
        }

        val ivMenuSend = binding.toolbarMedia.menu.findItem(R.id.menu_send)
        ivMenuSend.clicks().subscribeWithRxLife {
            setFragmentResult(REQUEST_KEY_MEDIA, bundleOf(KEY_MEDIA to viewModel.selectMediaList))
            findNavController().navigateUp()
        }

        viewModel.mediaList.observe(viewLifecycleOwner) {
            if (it == null || it.isEmpty()) {
                ivMenuSend.isVisible = false
                binding.rvMedia.isVisible = false
                binding.itemEmpty.clEmpty.isVisible = true
            } else {
                ivMenuSend.isVisible = it.any { it.isChoice }
                binding.rvMedia.isVisible = true
                binding.itemEmpty.clEmpty.isVisible = false
            }
            adapter.submitList(it)
        }

    }

}