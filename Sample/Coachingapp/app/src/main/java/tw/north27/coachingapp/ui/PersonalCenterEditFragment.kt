package tw.north27.coachingapp.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.yujie.utilmodule.UserPref
import com.yujie.utilmodule.adapter.bindImg
import com.yujie.utilmodule.base.BaseFragment
import com.yujie.utilmodule.ext.clicksObserve
import com.yujie.utilmodule.util.ViewState
import com.yujie.utilmodule.util.logD
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import tw.north27.coachingapp.R
import tw.north27.coachingapp.adapter.GenderAdapter
import tw.north27.coachingapp.adapter.GradeAdapter
import tw.north27.coachingapp.databinding.FragmentPersonalCenterEditBinding
import tw.north27.coachingapp.model.UserInfo
import tw.north27.coachingapp.viewModel.PersonalViewModel
import java.text.SimpleDateFormat
import java.util.*

class PersonalCenterEditFragment : BaseFragment<FragmentPersonalCenterEditBinding>(R.layout.fragment_personal_center_edit) {

    override val viewBind: (View) -> FragmentPersonalCenterEditBinding
        get() = FragmentPersonalCenterEditBinding::bind

    private val viewModel by sharedViewModel<PersonalViewModel>()

    private val genderAdapter = GenderAdapter()

    private val gradeAdapter = GradeAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            itemToolbarNormal.tvTitle.text = getString(R.string.edit)
            ivBackground.bindImg(resId = viewModel.bgRes.value)
            itemPersonalCenterUserEdit.apply {
                spGender.adapter = genderAdapter
                spGrade.adapter = gradeAdapter
            }
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

        viewModel.genderList.observe(viewLifecycleOwner) {
            if (viewModel.userState.value is ViewState.Data) {
                val userInfo = (viewModel.userState.value as ViewState.Data).data
                genderAdapter.submitData(it)
                binding.itemPersonalCenterUserEdit.spGender.setSelection(it.indexOfFirst { it.first == userInfo.gender })
            }
        }

//        viewModel.gradeListState.observe(viewLifecycleOwner) {
//            logD("it is ViewState.Data = ${it is ViewState.Data}")
//            if (it is ViewState.Data && viewModel.userState.value is ViewState.Data) {
//                val userInfo = (viewModel.userState.value as ViewState.Data).data
//                gradeAdapter.submitData(it.data)
//                binding.itemPersonalCenterUserEdit.spGrade.setSelection(it.data.indexOf(userInfo.studentInfo?.gradeId))
//            }
//        }

        binding.itemToolbarNormal.ivBack.clicksObserve(owner = viewLifecycleOwner) {
            findNavController().navigateUp()
        }

        binding.itemPersonalCenterInfoEdit.btnBirthday.clicksObserve(owner = viewLifecycleOwner) {
            if (viewModel.userState.value is ViewState.Data) {
                val userInfo = (viewModel.userState.value as ViewState.Data).data
                val calendar: Calendar
                val year: Int
                val month: Int
                val day: Int
                if (userInfo.birthday != null) {
                    calendar = GregorianCalendar()
                    calendar.time = SimpleDateFormat("yyyy-MM-dd").parse(binding.itemPersonalCenterInfoEdit.btnBirthday.text.toString())
                    year = calendar[Calendar.YEAR]
                    month = calendar[Calendar.MONTH]
                    day = calendar[Calendar.DAY_OF_MONTH]
                } else {
                    calendar = Calendar.getInstance()
                    year = calendar[Calendar.YEAR]
                    month = calendar[Calendar.MONTH]
                    day = calendar[Calendar.DAY_OF_MONTH]
                }
                DatePickerDialog(cxt, { view, year, month, dayOfMonth ->
                    binding.itemPersonalCenterInfoEdit.btnBirthday.text = String.format("%d-%d-%d", year, (month + 1), dayOfMonth)
                }, year, month, day).apply {
                    datePicker.maxDate = Date().time
                }.show()
            }
        }

        binding.btnEdit.clicksObserve(owner = viewLifecycleOwner) {
            if (viewModel.userState.value is ViewState.Data) {
                val userInfo = (viewModel.userState.value as ViewState.Data).data
                var isNotEmpty = false
                var emptyStr = ""
                if (binding.itemPersonalCenterUserEdit.etName.text.toString().trim().isEmpty()) {
                    isNotEmpty = true
                    emptyStr = getString(R.string.name)
                }
                if (userInfo.auth == UserPref.Authority.STUDENT && binding.itemPersonalCenterUserEdit.etSchool.text.toString().trim().isEmpty()) {
                    isNotEmpty = true
                    emptyStr += if (emptyStr == "") getString(R.string.school) else ", ${getString(R.string.school)}"
                }
                if (isNotEmpty)
                    Snackbar.make(binding.btnEdit, "$emptyStr${getString(R.string.not_empty)}", Snackbar.LENGTH_SHORT).show()
                else {
//                    LoadingDialogFragment.show(parentFragmentManager)

//                    viewModel
//                    LoadingDialogFragment.dismiss()
                    findNavController().navigateUp()
                }
            } else {
                Toast.makeText(cxt, getString(R.string.edit_failed), Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }
        }

    }

    private fun setData(userInfo: UserInfo) {
        binding.apply {
            userInfo.bgPath?.let { ivBackground.bindImg(url = it) }
            ivAvatar.bindImg(url = userInfo.avatarPath, roundingRadius = 10)
            itemPersonalCenterUserEdit.apply {
                tvId.text = userInfo.id.toString()
                etName.setText(userInfo.name)
                llSchool.isVisible = (userInfo.auth == UserPref.Authority.STUDENT)
                etSchool.setText(userInfo.studentInfo?.school)
                llGrade.isVisible = (userInfo.auth == UserPref.Authority.STUDENT)
                tvAuth.text = when (userInfo.auth) {
                    UserPref.Authority.STUDENT -> getString(R.string.student)
                    UserPref.Authority.TEACHER -> getString(R.string.teacher)
                    else -> getString(R.string.unknown)
                }
            }
            itemPersonalCenterStateMsgEdit.etName.setText(userInfo.intro)
            itemPersonalCenterInfoEdit.apply {
                btnBirthday.text = userInfo.birthday?.let { SimpleDateFormat("yyyy-MM-dd", Locale.TAIWAN).format(userInfo.birthday) } ?: getString(R.string.enter_birthday)
                etHomePhone.setText(userInfo.homePhone)
                etCellPhone.setText(userInfo.cellPhone)
                etEmail.setText(userInfo.email)
            }
        }
    }
}