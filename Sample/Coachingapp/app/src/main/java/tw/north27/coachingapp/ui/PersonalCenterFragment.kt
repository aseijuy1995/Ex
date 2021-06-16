package tw.north27.coachingapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.yujie.utilmodule.adapter.bindImg
import com.yujie.utilmodule.base.BaseFragment
import com.yujie.utilmodule.ext.clicksObserve
import com.yujie.utilmodule.ext.isVisible
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
        get() {
            val index = backgroundResList.indices.random()
            println("index = $index")
            return backgroundResList[index]
        }

    @SuppressLint("NewApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            ivBackground.bindImg(resId = backgroundRes)
            ctlLayout.apply {
                setCollapsedTitleTextColor(cxt.getColorStateList(R.color.white))
                setExpandedTitleTextColor(cxt.getColorStateList(R.color.white))
            }
            itemPersonalCenterStateMsg.tvStateMsg.isSelected = true
        }

        //
        viewModel.userInfoState.observe(viewLifecycleOwner) {
            binding.itemShimmer.sflView.isVisible = (it is ViewState.Load)
            binding.itemEmpty.root.isVisible = (it is ViewState.Empty)
            binding.nsvView.isVisible = (it is ViewState.Data)
            binding.itemError.root.isVisible = (it is ViewState.Error)
            binding.itemNetwork.root.isVisible = (it is ViewState.Network)
            when (it) {
//                is ViewState.Initial -> {
//                }
//                is ViewState.Load -> {
//                }
//                is ViewState.Empty -> {
//                }

                is ViewState.Data -> {
                    val userInfo = it.data
                    binding.itemPersonalCenterUser.ivAvatar.bindImg(
                        url = userInfo.avatarPath,
                        roundingRadius = 10
                    )
                    binding.userInfo = it.data
                }
                //FIXME　整合處理各頁面錯誤
                is ViewState.Error -> {
                }
                is ViewState.Network -> {

                }

            }
        }

        //編輯
        binding.itemPersonalCenterUser.ivEdit.clicksObserve(owner = viewLifecycleOwner) {
            findNavController().navigate(PersonalCenterFragmentDirections.actionFragmentPersonalCenterToFragmentPersonalCenterEdit())
        }
        //歷程
        binding.itemPersonalCenterStudy.itemCourse.root.clicksObserve(owner = viewLifecycleOwner) {

        }
        //分析
        binding.itemPersonalCenterStudy.itemAnalysis.root.clicksObserve(owner = viewLifecycleOwner) {

        }
        //回覆提醒
        binding.itemPersonalCenterSetting.itemReplyRemind.root.clicksObserve(owner = viewLifecycleOwner) {

        }
        //訊息提醒
        binding.itemPersonalCenterSetting.itemMsgRemind.root.clicksObserve(owner = viewLifecycleOwner) {

        }
        //推廣連結
        binding.itemPersonalCenterShare.itemLink.root.clicksObserve(owner = viewLifecycleOwner) {

        }
        //關於Coaching
        binding.itemPersonalCenterAbout.itemAboutCoaching.root.clicksObserve(owner = viewLifecycleOwner) {

        }
        //常見問題
        binding.itemPersonalCenterAbout.itemCommonProblem.root.clicksObserve(owner = viewLifecycleOwner) {

        }
        //隱私政策
        binding.itemPersonalCenterAbout.itemPrivacyPolicy.root.clicksObserve(owner = viewLifecycleOwner) {

        }
        //聯繫我們
        binding.itemPersonalCenterAbout.itemContactUs.root.clicksObserve(owner = viewLifecycleOwner) {

        }
        //登出
        binding.itemPersonalCenterSignOut.itemSignOut.root.clicksObserve(owner = viewLifecycleOwner) {
            findNavController().navigate(PersonalCenterFragmentDirections.actionFragmentPersonalCenterToFragmentSignOutDialog())
        }


//        doubleClickToExit()
    }
}