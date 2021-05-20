package tw.north27.coachingapp.ui2.fragment.main

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.yujie.basemodule.BaseFragment
import tw.north27.coachingapp.NavGraphDirections
import tw.north27.coachingapp.R
import tw.north27.coachingapp.databinding.FragmentPersonalCenterBinding
import tw.north27.coachingapp.ext2.clicksObserve


class PersonalCenterFragment : BaseFragment<FragmentPersonalCenterBinding>(R.layout.fragment_personal_center) {

    override val viewBindingFactory: (View) -> FragmentPersonalCenterBinding
        get() = FragmentPersonalCenterBinding::bind

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        doubleClickToExit()

        binding.itemSignOut.root.clicksObserve(owner = viewLifecycleOwner) {
            findNavController().navigate(NavGraphDirections.actionToFragmentSignOutDialog())
        }
    }
}