package tw.north27.coachingapp.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.yujie.utilmodule.UserPref
import com.yujie.utilmodule.adapter.bindImg
import com.yujie.utilmodule.base.BaseFragment
import com.yujie.utilmodule.ext.clicksObserve
import com.yujie.utilmodule.util.ViewState
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import tw.north27.coachingapp.R
import tw.north27.coachingapp.adapter.GenderAdapter
import tw.north27.coachingapp.adapter.GradeAdapter
import tw.north27.coachingapp.databinding.FragmentPersonalCenterEditBinding
import tw.north27.coachingapp.model.UserInfo
import tw.north27.coachingapp.viewModel.PersonalCenterViewModel
import java.text.SimpleDateFormat
import java.util.*

class PersonalCenterEditFragment : BaseFragment<FragmentPersonalCenterEditBinding>(R.layout.fragment_personal_center_edit) {

    override val viewBindingFactory: (View) -> FragmentPersonalCenterEditBinding
        get() = FragmentPersonalCenterEditBinding::bind

    private val viewModel by sharedViewModel<PersonalCenterViewModel>()

    private val genderAdapter = GenderAdapter()

    private val gradeAdapter = GradeAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            itemToolbarNormal.tvTitle.text = getString(R.string.edit)
            ivBackground.bindImg(resId = viewModel.backgroundRes.value)
            itemPersonalCenterUserEdit.apply {
                spGender.adapter = genderAdapter
                spGrade.adapter = gradeAdapter
            }
        }

        binding.itemToolbarNormal.ivBack.clicksObserve(owner = viewLifecycleOwner) {
            findNavController().navigateUp()
        }


        viewModel.userState.observe(viewLifecycleOwner) {
            when (it) {
                is ViewState.Data -> {
                    val userInfo = it.data
                    setData(userInfo)
                }
                //FIXME　整合處理各頁面錯誤
                is ViewState.Error, is ViewState.Network -> {
                }
            }
        }


//        doubleClickToExit()

//        binding.itemSignOut.root.clicksObserve(owner = viewLifecycleOwner) {
//            findNavController().navigate(NavGraphDirections.actionToFragmentSignOutDialog())
//        }
    }

    private fun setData(userInfo: UserInfo) {
        binding.apply {
            userInfo.backgroundPath?.let { ivBackground.bindImg(url = it) }
            ivAvatar.bindImg(url = userInfo.avatarPath, roundingRadius = 10)
            itemPersonalCenterUserEdit.apply {
                tvId.text = userInfo.id.toString()
                etName.setText(userInfo.name)
                etSchool.setText(userInfo.studentInfo?.school)
                tvAuth.text = when (userInfo.auth) {
                    UserPref.Authority.STUDENT -> getString(R.string.student)
                    UserPref.Authority.TEACHER -> getString(R.string.teacher)
                    else -> getString(R.string.unknown)
                }
            }
            itemPersonalCenterStateMsgEdit.etName.setText(userInfo.desc)
            itemPersonalCenterInfoEdit.apply {
                btnBirthday.text = SimpleDateFormat("yyyy-MM-dd", Locale.TAIWAN).format(userInfo.birthday)
                etHomePhone.setText(userInfo.homePhone)
                etCellPhone.setText(userInfo.cellPhone)
                etEmail.setText(userInfo.email)
            }
        }

        viewModel.genderList.observe(viewLifecycleOwner) {
            genderAdapter.submitData(it)
            binding.itemPersonalCenterUserEdit.spGender.setSelection(
                if (userInfo.gender == null) 0 else it.indexOf(userInfo.gender)
            )
        }
        viewModel.gradeListState.observe(viewLifecycleOwner) {
            if (it is ViewState.Data) {
                gradeAdapter.submitData(it.data)
                binding.itemPersonalCenterUserEdit.spGrade.setSelection(
                    if (userInfo.studentInfo?.grade == null) 0 else it.data.indexOf(userInfo.studentInfo.grade)
                )
            }
        }
    }
}