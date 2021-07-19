package tw.north27.coachingapp.ui.launch2.ask

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.yujie.core_lib.base.BaseBottomSheetDialogFragment
import com.yujie.core_lib.ext.clicksObserve
import com.yujie.core_lib.ext.observe
import com.yujie.core_lib.util.ViewState
import org.koin.androidx.viewmodel.ext.android.viewModel
import tw.north27.coachingapp.R
import tw.north27.coachingapp.adapter.AlbumListAdapter
import tw.north27.coachingapp.adapter.AudioListAdapter
import tw.north27.coachingapp.adapter.VideoListAdapter
import tw.north27.coachingapp.databinding.FragmentAskRoomMediaDialogBinding
import tw.north27.coachingapp.model.transfer.SendMode
import tw.north27.coachingapp.viewModel.AskRoomMediaViewModel

class AskRoomMediaDialogFragment : BaseBottomSheetDialogFragment<FragmentAskRoomMediaDialogBinding>(R.layout.fragment_ask_room_media_dialog) {

    override val viewBind: (View) -> FragmentAskRoomMediaDialogBinding
        get() = FragmentAskRoomMediaDialogBinding::bind

    private val viewModel by viewModel<AskRoomMediaViewModel>()

    private val sendMode: SendMode?
        get() = arguments?.getParcelable<SendMode>("sendMode") ?: kotlin.run {
            findNavController().navigateUp()
            null
        }

    private val albumListAdapter = AlbumListAdapter()

    private val audioListAdapter = AudioListAdapter()

    private val videoListAdapter = VideoListAdapter()

    companion object {
        val REQUEST_KEY_ASK_ROOM_MEDIA = "REQUEST_KEY_ASK_ROOM_MEDIA"

        val KEY_MIME_TYPE = "KEY_MIME_TYPE"

        val KEY_MEDIA_LIST = "KEY_MEDIA_LIST"
    }

    override fun getTheme(): Int {
        return R.style.BottomSheetTheme_TopRadius15
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvTitle.text = getString(R.string.choose_please)
        binding.ivSend.isEnabled = false

        when (sendMode) {
            SendMode.ALBUM -> {
                binding.rvMedia.apply {
                    layoutManager = GridLayoutManager(cxt, 3)
                    adapter = albumListAdapter
                }
                albumListAdapter.config = viewModel.albumConfig
                viewModel.mediaImageListState.observe(viewLifecycleOwner) {
                    binding.itemEmpty.root.isVisible = (it is ViewState.Empty)
                    binding.rvMedia.isVisible = (it is ViewState.Data)
                    when (it) {
                        is ViewState.Data -> {
                            val mediaList = it.data
                            albumListAdapter.submitList(mediaList)
                        }
                    }
                }
                albumListAdapter.toastRelay.observe(viewLifecycleOwner) {
                    Toast.makeText(cxt, it, Toast.LENGTH_SHORT).show()
                }
                albumListAdapter.itemSelectRelay.observe(viewLifecycleOwner) {
                    if (albumListAdapter.selectMediaDataList.isNotEmpty()) {
                        binding.tvTitle.text = String.format(getString(R.string.select_count), albumListAdapter.selectMediaDataList.size)
                        binding.ivSend.isEnabled = true
                    } else {
                        binding.tvTitle.text = getString(R.string.choose_please)
                        binding.ivSend.isEnabled = false
                    }
                }
                albumListAdapter.itemClickRelay.observe(viewLifecycleOwner) {
//                    lifecycleScope.launch {
//                        delay(500)
////                        findNavController().navigate(ChatRoomFragmentDirections.actionFragmentChatRoomToFragmentMediaPhoto(it.second.data))
//                    }
                }
                viewModel.fetchMediaImage()
            }

            SendMode.AUDIO -> {
                binding.rvMedia.apply {
                    layoutManager = LinearLayoutManager(cxt)
                    addItemDecoration(DividerItemDecoration(cxt, LinearLayoutManager.VERTICAL).apply {
                        setDrawable(ContextCompat.getDrawable(cxt, R.drawable.shape_size_height_2_solid_gray) ?: return)
                    })
                    adapter = audioListAdapter
                }
                audioListAdapter.config = viewModel.audioConfig
                viewModel.mediaAudioListState.observe(viewLifecycleOwner) {
                    binding.itemEmpty.root.isVisible = (it is ViewState.Empty)
                    binding.rvMedia.isVisible = (it is ViewState.Data)
                    when (it) {
                        is ViewState.Data -> {
                            val mediaList = it.data
                            audioListAdapter.submitList(mediaList)
                        }
                    }
                }
                audioListAdapter.toastRelay.observe(viewLifecycleOwner) {
                    Toast.makeText(cxt, it, Toast.LENGTH_SHORT).show()
                }
                audioListAdapter.itemSelectRelay.observe(viewLifecycleOwner) {
                    if (audioListAdapter.selectMediaDataList.isNotEmpty()) {
                        binding.tvTitle.text = String.format(getString(R.string.select_count), audioListAdapter.selectMediaDataList.size)
                        binding.ivSend.isEnabled = true
                    } else {
                        binding.tvTitle.text = getString(R.string.choose_please)
                        binding.ivSend.isEnabled = false
                    }
                }
                audioListAdapter.itemClickRelay.observe(viewLifecycleOwner) {
//                    lifecycleScope.launch {
//                        delay(500)
////                        findNavController().navigate(ChatRoomFragmentDirections.actionFragmentChatRoomToFragmentMediaPhoto(it.second.data))
//                    }
                }
                viewModel.fetchMediaAudio()
            }
            SendMode.FILM -> {
                binding.rvMedia.apply {
                    layoutManager = GridLayoutManager(cxt, 3)
                    adapter = videoListAdapter
                }
                videoListAdapter.config = viewModel.videoConfig
                viewModel.mediaVideoListState.observe(viewLifecycleOwner) {
                    binding.itemEmpty.root.isVisible = (it is ViewState.Empty)
                    binding.rvMedia.isVisible = (it is ViewState.Data)
                    when (it) {
                        is ViewState.Data -> {
                            val mediaList = it.data
                            videoListAdapter.submitList(mediaList)
                        }
                    }
                }
                videoListAdapter.toastRelay.observe(viewLifecycleOwner) {
                    Toast.makeText(cxt, it, Toast.LENGTH_SHORT).show()
                }
                videoListAdapter.itemSelectRelay.observe(viewLifecycleOwner) {
                    if (videoListAdapter.selectMediaDataList.isNotEmpty()) {
                        binding.tvTitle.text = String.format(getString(R.string.select_count), videoListAdapter.selectMediaDataList.size)
                        binding.ivSend.isEnabled = true
                    } else {
                        binding.tvTitle.text = getString(R.string.choose_please)
                        binding.ivSend.isEnabled = false
                    }
                }
                videoListAdapter.itemClickRelay.observe(viewLifecycleOwner) {
//                    lifecycleScope.launch {
//                        delay(500)
////                        findNavController().navigate(ChatRoomFragmentDirections.actionFragmentChatRoomToFragmentMediaPhoto(it.second.data))
//                    }
                }
                viewModel.fetchMediaVideo()

//                /**
//                 * 放大播放影片
//                 * */
//                adapter.itemClickRelay.subscribeWithRxLife {
////                    findNavController().navigate(MediaListDialogFragmentDirections.actionFragmentMediaListDialogToFragmentMediaPreview(it, From.MEDIA_LIST))
//                }
            }
        }

        binding.ivBack.clicksObserve(owner = viewLifecycleOwner) {
            findNavController().navigateUp()
        }

        binding.ivSend.clicksObserve(owner = viewLifecycleOwner) {
            //            setFragmentResult(
//                REQUEST_KEY_MEDIA, bundleOf(
////                    KEY_MIME_TYPE to args.mimeType,
//                    KEY_MEDIA_LIST to viewModel.choiceMediaList.value
//                )
//            )
//            findNavController().navigateUp()
        }
//
//        viewModel.choiceMediaList.observe(viewLifecycleOwner) {
//            ivMenuSend.isVisible = !it.isNullOrEmpty()
//        }
//
//        viewModel.mediaList.observe(viewLifecycleOwner) {
//            if (it.isNullOrEmpty()) {
//                binding.rvMedia.isVisible = false
//                binding.itemEmpty.root.isVisible = true
//            } else {
//                binding.rvMedia.isVisible = true
//                binding.itemEmpty.root.isVisible = false
//            }
//            adapter.submitList(it)
//        }

    }
}