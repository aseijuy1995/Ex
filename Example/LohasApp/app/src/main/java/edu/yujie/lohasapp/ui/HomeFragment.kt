package edu.yujie.lohasapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import edu.yujie.lohasapp.adapter.AdvertPagerAdapter
import edu.yujie.lohasapp.databinding.FragHomeBinding
import edu.yujie.lohasapp.viewModel.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {
    private lateinit var binding: FragHomeBinding
    private lateinit var advertAdapter: AdvertPagerAdapter
    private val viewModel by viewModel<HomeViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragHomeBinding.inflate(inflater, container, false)
        advertAdapter = AdvertPagerAdapter(this)

        viewModel.getAdvert().observe(viewLifecycleOwner) {
//            (requireActivity() as MainActivity).binding.shimmerLayout.startShimmerAnimation()
            lifecycleScope.launch(Dispatchers.IO) {
                delay(1500)
                withContext(Dispatchers.Main) {
                    advertAdapter.submit(it)
//                    (requireActivity() as MainActivity).binding.shimmerLayout.stopShimmerAnimation()
                }
            }
        }




        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.vp2Advert.adapter = advertAdapter
    }
}