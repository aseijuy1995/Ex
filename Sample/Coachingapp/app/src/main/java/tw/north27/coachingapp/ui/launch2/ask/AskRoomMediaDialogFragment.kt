package tw.north27.coachingapp.ui.launch2.ask

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.yujie.utilmodule.base.BaseBottomSheetDialogFragment
import com.yujie.utilmodule.ext.clicksObserve
import org.koin.androidx.viewmodel.ext.android.viewModel
import tw.north27.coachingapp.R
import tw.north27.coachingapp.databinding.FragmentAskRoomMediaDialogBinding
import tw.north27.coachingapp.media.AskRoomMediaViewModel

class AskRoomMediaDialogFragment : BaseBottomSheetDialogFragment<FragmentAskRoomMediaDialogBinding>(R.layout.fragment_ask_room_media_dialog) {

    override val viewBind: (View) -> FragmentAskRoomMediaDialogBinding
        get() = FragmentAskRoomMediaDialogBinding::bind

    private val viewModel by viewModel<AskRoomMediaViewModel>()

    private val sendMode: AskRoomModeDialogFragment.SendMode?
        get() = arguments?.getParcelable<AskRoomModeDialogFragment.SendMode>("sendMode") ?: kotlin.run {
            findNavController().navigateUp()
            null
        }

//    private lateinit var adapter: AskRoomMediaListAdapter

    companion object {
        val REQUEST_KEY_MEDIA = "REQUEST_KEY_MEDIA"

        val KEY_MIME_TYPE = "KEY_MIME_TYPE"

        val KEY_MEDIA_LIST = "KEY_MEDIA_LIST"
    }

    override fun getTheme(): Int {
        return R.style.BottomSheetTheme_TopRadius15
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvTitle.text = getString(R.string.choose_please)

        when (sendMode) {
            AskRoomModeDialogFragment.SendMode.AUDIO -> {
//                adapter = ChatRoomMediaListAdapter(args.mimeType, viewModel.audioSetting)
//                binding.rvMedia.apply {
//                    addItemDecoration(DividerItemDecoration(cxt, LinearLayoutManager.VERTICAL).apply {
//                        setDrawable(ContextCompat.getDrawable(cxt, R.drawable.shape_size_height_1_solid_gray) ?: return)
//                    })
//                    layoutManager = LinearLayoutManager(cxt)
//                    adapter = this@ChatRoomMediaDialogFragment.adapter
//                }
//                //audio
//                viewModel.getMediaAudio().subscribeWithRxLife {
//                    val mediaList = it.find { it.albumName == MEDIA_ALBUM_AUDIO }?.mediaList
//                    viewModel.setMediaList(mediaList)
//                }
//                /**
//                 * 播放音訊
//                 * */
//                adapter.itemClickRelay.subscribeWithRxLife {
//
//                }
//                adapter.itemSelectRelay.subscribeWithRxLife {
//                    viewModel.setChoiceOfMedia(it.third)
//                }
            }
            AskRoomModeDialogFragment.SendMode.ALBUM -> {
//                adapter = ChatRoomMediaListAdapter(args.mimeType, viewModel.imageSetting)
//                binding.rvMedia.apply {
//                    layoutManager = GridLayoutManager(cxt, 3)
//                    adapter = this@ChatRoomMediaDialogFragment.adapter
//                }
//                //image
//                viewModel.getMediaImage().subscribeWithRxLife {
//                    val mediaList = it.find { it.albumName == MEDIA_ALBUM_IMAGE }?.mediaList
//                    viewModel.setMediaList(mediaList)
//                }
//                adapter.itemClickRelay.throttleFirst(500, TimeUnit.MILLISECONDS).subscribeWithRxLife {
//                    lifecycleScope.launch {
//                        delay(500)
////                        findNavController().navigate(ChatRoomFragmentDirections.actionFragmentChatRoomToFragmentMediaPhoto(it.second.data))
//                    }
//
//                }
//                adapter.itemSelectRelay.subscribeWithRxLife {
//                    viewModel.setChoiceOfMedia(it.third)
//                }
            }
            AskRoomModeDialogFragment.SendMode.VIDEO -> {
//                adapter = ChatRoomMediaListAdapter(args.mimeType, viewModel.videoSetting)
//                binding.rvMedia.apply {
//                    layoutManager = GridLayoutManager(cxt, 3)
//                    adapter = this@ChatRoomMediaDialogFragment.adapter
//                }
//                //video
//                viewModel.getMediaVideo().subscribeWithRxLife {
//                    val mediaList = it.find { it.albumName == MEDIA_ALBUM_VIDEO }?.mediaList
//                    viewModel.setMediaList(mediaList)
//                }
//                /**
//                 * 放大播放影片
//                 * */
//                adapter.itemClickRelay.subscribeWithRxLife {
////                    findNavController().navigate(MediaListDialogFragmentDirections.actionFragmentMediaListDialogToFragmentMediaPreview(it, From.MEDIA_LIST))
//                }
//                adapter.itemSelectRelay.subscribeWithRxLife {
//                    viewModel.setChoiceOfMedia(it.third)
//                }
            }
        }

//        adapter.toastRelay.subscribeWithRxLife {
//            Toast.makeText(cxt, it, Toast.LENGTH_SHORT).show()
//        }
//
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