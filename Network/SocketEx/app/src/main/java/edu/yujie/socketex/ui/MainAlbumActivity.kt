package edu.yujie.socketex.ui

import android.os.Bundle
import edu.yujie.socketex.R
import edu.yujie.socketex.album.GalleryViewModel
import edu.yujie.socketex.base.BaseActivity
import edu.yujie.socketex.databinding.ActivityMainAlbumBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainAlbumActivity : BaseActivity<ActivityMainAlbumBinding>() {
    private val viewModel by viewModel<GalleryViewModel>()

    override val layoutId: Int
        get() = R.layout.activity_main_album

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}