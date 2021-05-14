package tw.north27.coachingapp.ui2.fragment.main

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.jakewharton.rxbinding4.view.clicks
import org.koin.androidx.viewmodel.ext.android.viewModel
import tw.north27.coachingapp.R
import tw.north27.coachingapp.base.BaseDataBindingFragment
import tw.north27.coachingapp.databinding.FragmentMediaPhotoBinding
import tw.north27.coachingapp.media.MediaPhotoViewModel

class MediaPhotoFragment : BaseDataBindingFragment<FragmentMediaPhotoBinding>(R.layout.fragment_media_photo) {

    private val viewModel by viewModel<MediaPhotoViewModel>()

//    private val args: MediaPhotoFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@MediaPhotoFragment.viewModel
        }
        binding.ivBack.clicks().subscribeWithRxLife {
            findNavController().navigateUp()
        }

        /**
         * FIXME 當前先丟假資料，接正是數據需改為url
         * */
//        viewModel.setUrl(args.url)
    }

}