package tw.north27.coachingapp.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.yujie.utilmodule.base.BaseFragment
import com.yujie.utilmodule.ext.clicksObserve
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import tw.north27.coachingapp.R
import tw.north27.coachingapp.databinding.FragmentPersonalCenterEditBinding
import tw.north27.coachingapp.viewModel.PersonalCenterViewModel

class PersonalCenterEditFragment : BaseFragment<FragmentPersonalCenterEditBinding>(R.layout.fragment_personal_center_edit) {

    override val viewBindingFactory: (View) -> FragmentPersonalCenterEditBinding
        get() = FragmentPersonalCenterEditBinding::bind

    private val viewModel by sharedViewModel<PersonalCenterViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            itemToolbarNormal.tvTitle.text = getString(R.string.edit)
            ivBackground.setBackgroundResource(viewModel.backgroundRes.value!!)
        }

        binding.itemToolbarNormal.ivBack.clicksObserve(owner = viewLifecycleOwner) {
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