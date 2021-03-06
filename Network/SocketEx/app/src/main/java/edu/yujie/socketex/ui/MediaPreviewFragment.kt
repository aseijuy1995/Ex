package edu.yujie.socketex.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.google.android.exoplayer2.Player
import com.jakewharton.rxbinding4.view.clicks
import edu.yujie.socketex.R
import edu.yujie.socketex.bean.Media
import edu.yujie.socketex.databinding.FragmentMediaPreviewBinding
import edu.yujie.socketex.finish.base.fragment.BaseDataBindingFragment
import edu.yujie.socketex.finish.vm.MediaViewModel
import edu.yujie.socketex.listener.ExoPlayerAutoLifecycleObserver
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MediaPreviewFragment : BaseDataBindingFragment<FragmentMediaPreviewBinding>(R.layout.fragment_media_preview) {

    private val viewModel by sharedViewModel<MediaViewModel>()

    private lateinit var media: Media

    private lateinit var player: Player

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        getArgument()
        val subscribeWithLife = binding.ivBtnSend.clicks().subscribeWithLife {
            viewModel.sendMediaList()
            findNavController().navigateUp()
        }
    }

    private fun initView() {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@MediaPreviewFragment.viewModel
        }
    }

    private fun getArgument() {
        media = arguments?.getParcelable<Media>("media")!!
        if (!media.isSelect) media.isSelect = true
        viewModel.selectMedia(media, isPick = true)

        viewModel.isMimeTypeVideo.observe(viewLifecycleOwner) {
            if (it) {
                player = viewModel.buildPlayer(media)
                binding.playerView.player = player
                ExoPlayerAutoLifecycleObserver(this, player)
            }
        }
    }


}