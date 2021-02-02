package edu.yujie.socketex.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.google.android.exoplayer2.Player
import com.jakewharton.rxbinding4.view.clicks
import edu.yujie.socketex.R
import edu.yujie.socketex.finish.base.fragment.BaseDataBindingFragment
import edu.yujie.socketex.bean.Media
import edu.yujie.socketex.bean.MimeType
import edu.yujie.socketex.databinding.FragmentMediaPreviewBinding
import edu.yujie.socketex.listener.ExoPlayerAutoLifecycleObserver
import edu.yujie.socketex.vm.MediaViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

//class MediaPreviewFragment : BaseFragment<FragmentMediaPreviewBinding>() {
class MediaPreviewFragment : BaseDataBindingFragment<FragmentMediaPreviewBinding>(R.layout.fragment_media_preview) {

    private val viewModel by sharedViewModel<MediaViewModel>()

    private lateinit var media: Media

    private lateinit var player: Player

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        getArgument()
        binding.ivBtnSend.clicks().subscribeWithLife {
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
        if (media.isSelect == false)
            viewModel.selectMedia(media = media)
        if (media.mimeType.startsWith(MimeType.VIDEO.toString())) {
            player = viewModel.buildPlayer(media)
            binding.playerView.player = player
            ExoPlayerAutoLifecycleObserver(this, player)
        }
    }


}