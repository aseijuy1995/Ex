package edu.yujie.socketex.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.jakewharton.rxbinding4.view.clicks
import edu.yujie.socketex.R
import edu.yujie.socketex.base.BaseFragment
import edu.yujie.socketex.bean.Media
import edu.yujie.socketex.bean.MimeType
import edu.yujie.socketex.databinding.FragmentMediaPreviewBinding
import edu.yujie.socketex.vm.MediaViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MediaPreviewFragment : BaseFragment<FragmentMediaPreviewBinding>() {

    override val layoutId: Int
        get() = R.layout.fragment_media_preview

    private val viewModel by sharedViewModel<MediaViewModel>()

    private lateinit var media: Media

    private var player: Player? = null

    private var mediaItem: MediaItem? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getArgument()
        initView()

        binding.ivBtnSend
            .clicks()
            .subscribeWithLife {
                media.isSelect = true
                viewModel.selectMedia(media = media)
                viewModel.sendMedia()
                findNavController().navigateUp()
            }
    }

    private fun getArgument() {
        media = arguments?.getParcelable<Media>("media")!!
        viewModel.setMedia(media = media)
    }

    private fun initView() {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@MediaPreviewFragment.viewModel
        }

        when (media.mimeType) {
            MimeType.ALL.toString() -> {
            }
            MimeType.IMAGE.toString() -> {
            }
            MimeType.VIDEO.toString() -> {
                mediaItem = MediaItem.fromUri(media.data)
                player = SimpleExoPlayer.Builder(requireContext()).build().apply {
                    repeatMode = Player.REPEAT_MODE_ALL
                    setMediaItem(mediaItem!!)
                    prepare()
                    play()
                }
                binding.playerView.player = player
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()

        when (media.mimeType) {
            MimeType.ALL.toString() -> {
            }
            MimeType.IMAGE.toString() -> {

            }
            MimeType.VIDEO.toString() -> {
                player?.release()
            }
        }
    }
}