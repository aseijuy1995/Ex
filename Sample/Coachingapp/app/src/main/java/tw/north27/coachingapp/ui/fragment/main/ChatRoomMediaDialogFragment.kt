package tw.north27.coachingapp.ui.fragment.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding4.view.clicks
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import tw.north27.coachingapp.R
import tw.north27.coachingapp.base.BaseBottomSheetDialogFragment
import tw.north27.coachingapp.media.mediaStore.MimeType
import tw.north27.coachingapp.databinding.FragmentChatRoomMediaDialogBinding
import tw.north27.coachingapp.ext.dataBinding
import tw.north27.coachingapp.media.*
import tw.north27.coachingapp.media.mediaStore.MEDIA_ALBUM_AUDIO
import tw.north27.coachingapp.media.mediaStore.MEDIA_ALBUM_IMAGE
import tw.north27.coachingapp.media.mediaStore.MEDIA_ALBUM_VIDEO
import java.util.concurrent.TimeUnit

class ChatRoomMediaDialogFragment : BaseBottomSheetDialogFragment() {

    protected lateinit var cxt: Context

    private val binding by dataBinding<FragmentChatRoomMediaDialogBinding>(R.layout.fragment_chat_room_media_dialog)

    private val viewModel by viewModel<MediaViewModel>()

    private val args by navArgs<ChatRoomMediaDialogFragmentArgs>()

    private lateinit var adapter: ChatRoomMediaListAdapter

    companion object {
        val REQUEST_KEY_MEDIA = "REQUEST_KEY_MEDIA"

        val KEY_MIME_TYPE = "KEY_MIME_TYPE"

        val KEY_MEDIA_LIST = "KEY_MEDIA_LIST"
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
                adapter = ChatRoomMediaListAdapter(args.mimeType, viewModel.audioSetting)
                binding.rvMedia.apply {
                    addItemDecoration(DividerItemDecoration(cxt, LinearLayoutManager.VERTICAL).apply {
                        setDrawable(ContextCompat.getDrawable(cxt, R.drawable.shape_size_height_1_solid_gray) ?: return)
                    })
                    layoutManager = LinearLayoutManager(cxt)
                    adapter = this@ChatRoomMediaDialogFragment.adapter
                }
                //audio
                viewModel.getMediaAudio().subscribeWithRxLife {
                    val mediaList = it.find { it.albumName == MEDIA_ALBUM_AUDIO }?.mediaList
                    viewModel.setMediaList(mediaList)
                }
                /**
                 * 播放音訊
                 * */
                adapter.itemClickRelay.subscribeWithRxLife {

                }
                adapter.itemSelectRelay.subscribeWithRxLife {
                    viewModel.setChoiceOfMedia(it.third)
                }
            }
            MimeType.IMAGE -> {
                adapter = ChatRoomMediaListAdapter(args.mimeType, viewModel.imageSetting)
                binding.rvMedia.apply {
                    layoutManager = GridLayoutManager(cxt, 3)
                    adapter = this@ChatRoomMediaDialogFragment.adapter
                }
                //image
                viewModel.getMediaImage().subscribeWithRxLife {
                    val mediaList = it.find { it.albumName == MEDIA_ALBUM_IMAGE }?.mediaList
                    viewModel.setMediaList(mediaList)
                }
                adapter.itemClickRelay.throttleFirst(500, TimeUnit.MILLISECONDS).subscribeWithRxLife {
                    lifecycleScope.launch {
                        delay(500)
                        findNavController().navigate(ChatRoomFragmentDirections.actionFragmentChatRoomToFragmentMediaPhoto(it.second.data))
                    }

                }
                adapter.itemSelectRelay.subscribeWithRxLife {
                    viewModel.setChoiceOfMedia(it.third)
                }
            }
            MimeType.VIDEO -> {
                adapter = ChatRoomMediaListAdapter(args.mimeType, viewModel.videoSetting)
                binding.rvMedia.apply {
                    layoutManager = GridLayoutManager(cxt, 3)
                    adapter = this@ChatRoomMediaDialogFragment.adapter
                }
                //video
                viewModel.getMediaVideo().subscribeWithRxLife {
                    val mediaList = it.find { it.albumName == MEDIA_ALBUM_VIDEO }?.mediaList
                    viewModel.setMediaList(mediaList)
                }
                /**
                 * 放大播放影片
                 * */
                adapter.itemClickRelay.subscribeWithRxLife {
//                    findNavController().navigate(MediaListDialogFragmentDirections.actionFragmentMediaListDialogToFragmentMediaPreview(it, From.MEDIA_LIST))
                }
                adapter.itemSelectRelay.subscribeWithRxLife {
                    viewModel.setChoiceOfMedia(it.third)
                }
            }
        }

        adapter.toastRelay.subscribeWithRxLife {
            Toast.makeText(cxt, it, Toast.LENGTH_SHORT).show()
        }

        val ivMenuSend = binding.toolbarMedia.menu.findItem(R.id.menu_send)
        ivMenuSend.clicks().subscribeWithRxLife {
            setFragmentResult(
                REQUEST_KEY_MEDIA, bundleOf(
                    KEY_MIME_TYPE to args.mimeType,
                    KEY_MEDIA_LIST to viewModel.choiceMediaList.value
                )
            )
            findNavController().navigateUp()
        }

        viewModel.choiceMediaList.observe(viewLifecycleOwner) {
            ivMenuSend.isVisible = !it.isNullOrEmpty()
        }

        viewModel.mediaList.observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) {
                binding.rvMedia.isVisible = false
                binding.itemEmpty.root.isVisible = true
            } else {
                binding.rvMedia.isVisible = true
                binding.itemEmpty.root.isVisible = false
            }
            adapter.submitList(it)
        }

    }

}