package tw.north27.coachingapp.ui2.fragment.main

import android.os.Bundle
import android.view.View
import tw.north27.coachingapp.R
import tw.north27.coachingapp.base.BaseDataBindingFragment
import tw.north27.coachingapp.databinding.FragmentUserBinding


class UserFragment : BaseDataBindingFragment<FragmentUserBinding>(R.layout.fragment_user) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        doubleClickToExit()

        binding.itemSignOut.root.clickThrottleFirst().subscribeWithRxLife {
//            findNavController().navigate(UserFragmentDirections.actionFragmentUserToFragmentExitDialog())
        }
    }
}