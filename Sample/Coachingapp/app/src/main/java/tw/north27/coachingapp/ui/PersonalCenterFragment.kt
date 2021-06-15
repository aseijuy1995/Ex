package tw.north27.coachingapp.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.yujie.utilmodule.adapter.bindImg
import com.yujie.utilmodule.base.BaseFragment
import com.yujie.utilmodule.ext.clicksObserve
import com.yujie.utilmodule.util.ViewState
import org.koin.androidx.viewmodel.ext.android.viewModel
import tw.north27.coachingapp.R
import tw.north27.coachingapp.databinding.FragmentPersonalCenterBinding
import tw.north27.coachingapp.viewModel.PersonalCenterViewModel

class PersonalCenterFragment : BaseFragment<FragmentPersonalCenterBinding>(R.layout.fragment_personal_center) {

    override val viewBindingFactory: (View) -> FragmentPersonalCenterBinding
        get() = FragmentPersonalCenterBinding::bind

    private val viewModel by viewModel<PersonalCenterViewModel>()

    private val backgroundResList = listOf<Int>(
        R.drawable.ic_personal_center_background1,
        R.drawable.ic_personal_center_background2,
        R.drawable.ic_personal_center_background3,
        R.drawable.ic_personal_center_background4,
        R.drawable.ic_personal_center_background5,
        R.drawable.ic_personal_center_background6,
        R.drawable.ic_personal_center_background7,
        R.drawable.ic_personal_center_background8,
        R.drawable.ic_personal_center_background9
    )

    val backgroundRes: Int
        get() = backgroundResList[backgroundResList.indices.random()]

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.ivBackground.setBackgroundResource(backgroundRes)
        binding.itemPersonalCenterStateMsg.tvStateMsg.isSelected = true
        viewModel.getUserInfo()

        viewModel.userInfoState.observe(viewLifecycleOwner) {
            binding.itemShimmer.sflView.isVisible = (it is ViewState.Load)
//            binding.itemEmpty.root.isVisible = (it is ViewState.Empty)
//            binding.rvView.isVisible = (it is ViewState.Data)
//            binding.itemError.root.isVisible = (it is ViewState.Error)
//            binding.itemNetwork.root.isVisible = (it is ViewState.Network)
            when (it) {
                is ViewState.Load -> {
                }
                is ViewState.Empty -> {
                }
                is ViewState.Data -> {
                    val userInfo = it.data
                    binding.itemPersonalCenterUser.rivAvatar.bindImg(url = userInfo.avatarPath)
                    binding.userInfo = it.data
                }
                //FIXME　整合處理各頁面錯誤
                is ViewState.Error, is ViewState.Network -> {
                }

            }
        }

        binding.itemPersonalCenterUser.ivEdit.clicksObserve(owner = viewLifecycleOwner) {
            findNavController().navigate(PersonalCenterFragmentDirections.actionFragmentPersonalCenterToFragmentPersonalCenterEdit())
        }


//        doubleClickToExit()

//        binding.itemSignOut.root.clicksObserve(owner = viewLifecycleOwner) {
//            findNavController().navigate(NavGraphDirections.actionToFragmentSignOutDialog())
//        }
    }
}