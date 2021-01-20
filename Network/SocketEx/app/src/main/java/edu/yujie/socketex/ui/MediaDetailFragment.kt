package edu.yujie.socketex.ui

import android.os.Bundle
import android.view.View
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import edu.yujie.socketex.R
import edu.yujie.socketex.base.BaseFragment
import edu.yujie.socketex.bean.Media
import edu.yujie.socketex.databinding.FragmentMediaDetailBinding

class MediaDetailFragment : BaseFragment<FragmentMediaDetailBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_media_detail

    private lateinit var media: Media

    private lateinit var player: Player

    private lateinit var mediaItem: MediaItem

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initArguments()
        initView()
    }

    private fun initArguments() {
        media = arguments?.getParcelable<Media>("media")!!
    }

    private fun initView() {
        mediaItem = MediaItem.fromUri(media.data)

        player = SimpleExoPlayer.Builder(requireContext()).build().apply {
            repeatMode = Player.REPEAT_MODE_ALL
            setMediaItem(mediaItem)
            prepare()
            play()
        }
        binding.playerView.player = player

    }

    override fun onDestroyView() {
        super.onDestroyView()
        player.release()
    }
}