package tw.north27.coachingapp.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.yujie.utilmodule.base.BaseFragment
import com.yujie.utilmodule.ext.clicksObserve
import tw.north27.coachingapp.R
import tw.north27.coachingapp.databinding.FragmentPersonalCenterEditBinding

class PersonalCenterEditFragment : BaseFragment<FragmentPersonalCenterEditBinding>(R.layout.fragment_personal_center_edit) {

    override val viewBindingFactory: (View) -> FragmentPersonalCenterEditBinding
        get() = FragmentPersonalCenterEditBinding::bind

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner

        binding.tbPersonalCenterEdit.ivBack.clicksObserve(owner = viewLifecycleOwner) {
            findNavController().navigateUp()
        }

//        viewModel.getUserInfo()
//
//        viewModel.userInfoState.observe(viewLifecycleOwner) {
//            when (it) {
//                is ViewState.Load -> {
//                }
//                is ViewState.Empty -> {
//                }
//                is ViewState.Data -> {
//                    val userInfo = it.data
//                }
//                //FIXME　整合處理各頁面錯誤
//                is ViewState.Error, is ViewState.Network -> {
//                }
//
//            }
//        }


//        doubleClickToExit()

//        binding.itemSignOut.root.clicksObserve(owner = viewLifecycleOwner) {
//            findNavController().navigate(NavGraphDirections.actionToFragmentSignOutDialog())
//        }
    }
}