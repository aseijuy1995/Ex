package tw.north27.coachingapp.ui2.fragment.main

import android.os.Bundle
import android.view.View
import com.yujie.basemodule.viewBinding
import tw.north27.coachingapp.R
import tw.north27.coachingapp.base.BaseFragment
import tw.north27.coachingapp.databinding.FragmentMainHomeBinding

class MainHomeFragment : BaseFragment(R.layout.fragment_main_home) {

    private val binding by viewBinding<FragmentMainHomeBinding>(FragmentMainHomeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        doubleClickToExit()
    }


}