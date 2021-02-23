package tw.north27.coachingapp.ui.fragment.main

import android.os.Bundle
import android.view.View
import tw.north27.coachingapp.base.view.BaseViewBindingDialogFragment
import tw.north27.coachingapp.base.view.BaseViewBindingFragment
import tw.north27.coachingapp.databinding.FragmentHomeBinding

class HomeFragment:BaseViewBindingFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}