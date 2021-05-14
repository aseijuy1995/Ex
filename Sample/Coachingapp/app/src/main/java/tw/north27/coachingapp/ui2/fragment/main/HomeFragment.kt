package tw.north27.coachingapp.ui2.fragment.main

import android.os.Bundle
import android.view.View
import com.yujie.basemodule.viewBinding
import tw.north27.coachingapp.R
import tw.north27.coachingapp.base.BaseFragment
import tw.north27.coachingapp.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private val binding by viewBinding<FragmentHomeBinding>(FragmentHomeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        doubleClickToExit()
    }


}