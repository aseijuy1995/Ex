package edu.yujie.lohasapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ethanhua.skeleton.Skeleton
import edu.yujie.lohasapp.AdvertBean
import edu.yujie.lohasapp.R
import edu.yujie.lohasapp.databinding.FragmentAdvertBinding

class AdvertFragment : Fragment() {
    private lateinit var binding: FragmentAdvertBinding
    private var advert: AdvertBean? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAdvertBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            advert = this@AdvertFragment.advert
        }

        Skeleton.bind(binding.root).load(R.layout.fragment_advert).show()
        return binding.root
    }

    fun submit(advert: AdvertBean) {
        this.advert = advert
    }
}