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
import com.yujie.utilmodule.ext.visible
import com.yujie.utilmodule.util.ViewState
import org.koin.androidx.viewmodel.ext.android.viewModel
import tw.north27.coachingapp.R
import tw.north27.coachingapp.adapter.GenderAdapter
import tw.north27.coachingapp.adapter.GradeAdapter
import tw.north27.coachingapp.databinding.FragmentPersonalEditBinding
import tw.north27.coachingapp.model.Gender
import tw.north27.coachingapp.model.Grade
import tw.north27.coachingapp.model.UserInfo
import tw.north27.coachingapp.viewModel.PersonalEditViewModel
import java.text.SimpleDateFormat
import java.util.*

class PersonalEditFragment : BaseFragment<FragmentPersonalEditBinding>(R.layout.fragment_personal_edit) {

    override val viewBind: (View) -> FragmentPersonalEditBinding
        get() = FragmentPersonalEditBinding::bind

//    private val publicVM by sharedViewModel<PublicViewModel>()

    private val viewModel by viewModel<PersonalEditViewModel>()

    private val genderAdapter = GenderAdapter()

    private val gradeAdapter = GradeAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            itemToolbarNormal.tvTitle.text = getString(R.string.edit)
            itemData.apply {
                ivBg.bindImg(resId = viewModel.bgRes.value)
                itemPersonalUserEdit.apply {
                    spGender.adapter = genderAdapter
                    spGrade.adapter = gradeAdapter
                }
            }
        }

        viewModel.userState.observe(viewLifecycleOwner) {
            binding.itemPersonalEditLoad.sflView.visible = (it is ViewState.Load)
            binding.itemEmpty.root.isVisible = (it is ViewState.Empty)
            binding.itemData.root.isVisible = (it is ViewState.Data)
            binding.itemError.root.isVisible = (it is ViewState.Error)
            binding.itemNetwork.root.isVisible = (it is ViewState.Network)
        }

        viewModel.updateUserState.observe(viewLifecycleOwner) {
            if (it is ViewState.Load) LoadingDialogFragment.show(parentFragmentManager)
            if (it !is ViewState.Initial && it !is ViewState.Load) LoadingDialogFragment.dismiss()
            when (it) {
                is ViewState.Data -> {
                    if (it.data) {
                        findNavController().navigateUp()
                        Toast.makeText(cxt, getString(R.string.edit_success), Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(cxt, getString(R.string.edit_failed), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.itemToolbarNormal.ivBack.clicksObserve(owner = viewLifecycleOwner) {
            findNavController().navigateUp()
        }

        binding.itemData.itemPersonalInfoEdit.btnBirthday.clicksObserve(owner = viewLifecycleOwner) {
            if (viewModel.userState.value is ViewState.Data) {
                val userInfo = (viewModel.userState.value as ViewState.Data).data
                val calendar: Calendar
                val year: Int
                val month: Int
                val day: Int
                if (userInfo.birthday != null) {
                    calendar = GregorianCalendar()
                    calendar.time = SimpleDateFormat("yyyy-MM-dd").parse(binding.itemData.itemPersonalInfoEdit.btnBirthday.text.toString())
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
                    binding.itemData.itemPersonalInfoEdit.btnBirthday.text = String.format("%d-%d-%d", year, (month + 1), dayOfMonth)
                }, year, month, day).apply {
                    datePicker.maxDate = Date().time
                }.show()
            }
        }

        binding.itemData.btnEdit.clicksObserve(owner = viewLifecycleOwner) {
            if (viewModel.userState.value is ViewState.Data) {
                val userInfo = (viewModel.userState.value as ViewState.Data).data
                var isNotEmpty = false
                var emptyStr = ""
                if (binding.itemData.itemPersonalUserEdit.etName.text.toString().trim().isEmpty()) {
                    isNotEmpty = true
                    emptyStr = getString(R.string.name)
                }
                if (userInfo.auth == UserPref.Authority.STUDENT && binding.itemData.itemPersonalUserEdit.etSchool.text.toString().trim().isEmpty()) {
                    isNotEmpty = true
                    emptyStr += if (emptyStr == "") getString(R.string.school) else ", ${getString(R.string.school)}"
                }
                if (isNotEmpty)
                    Snackbar.make(binding.itemData.btnEdit, "$emptyStr${getString(R.string.not_empty)}", Snackbar.LENGTH_SHORT).show()
                else {
                    when (userInfo.auth) {
                        UserPref.Authority.STUDENT -> {
                            viewModel.updateUser(
                                bgPath = "",
                                avatarPath = "",
                                name = binding.itemData.itemPersonalUserEdit.etName.text.toString(),
                                gender = binding.itemData.itemPersonalUserEdit.spGender.selectedItem as Gender,
                                intro = binding.itemData.itemPersonalIntroEdit.etName.text.toString(),
                                birthday = try {
                                    SimpleDateFormat("yyyy-MM-dd").parse(binding.itemData.itemPersonalInfoEdit.btnBirthday.text.toString())
                                } catch (e: Exception) {
                                    null
                                },
                                cellPhone = binding.itemData.itemPersonalInfoEdit.etCellPhone.text.toString(),
                                homePhone = binding.itemData.itemPersonalInfoEdit.etHomePhone.text.toString(),
                                email = binding.itemData.itemPersonalInfoEdit.etEmail.text.toString(),
                                school = binding.itemData.itemPersonalUserEdit.etSchool.text.toString(),
                                gradeId = (binding.itemData.itemPersonalUserEdit.spGrade.selectedItem as Grade).id,
                            )

                        }
                        UserPref.Authority.TEACHER -> {
                            viewModel.updateUser(
                                bgPath = "",
                                avatarPath = "",
                                name = binding.itemData.itemPersonalUserEdit.etName.text.toString(),
                                gender = binding.itemData.itemPersonalUserEdit.spGender.selectedItem as Gender,
                                intro = binding.itemData.itemPersonalIntroEdit.etName.text.toString(),
                                birthday = try {
                                    SimpleDateFormat("yyyy-MM-dd").parse(binding.itemData.itemPersonalInfoEdit.btnBirthday.text.toString())
                                } catch (e: Exception) {
                                    null
                                },
                                cellPhone = binding.itemData.itemPersonalInfoEdit.etCellPhone.text.toString(),
                                homePhone = binding.itemData.itemPersonalInfoEdit.etHomePhone.text.toString(),
                                email = binding.itemData.itemPersonalInfoEdit.etEmail.text.toString()
                            )
                        }
                    }
                }
            } else {
                Toast.makeText(cxt, getString(R.string.edit_failed), Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }
        }
    }

    private fun setUiData(userInfo: UserInfo, gradeList: List<Grade>, genderList: List<Gender>?) {
        binding.itemData.apply {
            if (userInfo.bgUrl != null && userInfo.bgUrl.isNotEmpty())
                ivBg.bindImg(url = userInfo.bgUrl)
            ivAvatar.bindImg(url = userInfo.avatarUrl, roundingRadius = 5)
            itemPersonalUserEdit.apply {
                etName.setText(userInfo.name)
                tvAccount.text = userInfo.account
                tvAuth.text = when (userInfo.auth) {
                    UserPref.Authority.STUDENT -> getString(R.string.student)
                    UserPref.Authority.TEACHER -> getString(R.string.teacher)
                    else -> getString(R.string.unknown)
                }
                llSchool.isVisible = (userInfo.auth == UserPref.Authority.STUDENT)
                etSchool.setText(userInfo.studentInfo?.school)
                llGrade.isVisible = (userInfo.auth == UserPref.Authority.STUDENT)

            }
            itemPersonalIntroEdit.etName.setText(userInfo.intro)
            itemPersonalInfoEdit.apply {
                btnBirthday.text = userInfo.birthday?.let { SimpleDateFormat("yyyy-MM-dd", Locale.TAIWAN).format(userInfo.birthday) } ?: getString(R.string.enter_birthday)
                etHomePhone.setText(userInfo.homePhone)
                etCellPhone.setText(userInfo.cellPhone)
                etEmail.setText(userInfo.email)
            }
        }
        genderAdapter.submitData(genderList)
        binding.itemData.itemPersonalUserEdit.spGender.setSelection(genderList?.indexOfFirst { it == userInfo.gender }!!)
        gradeAdapter.submitData(gradeList)
        binding.itemData.itemPersonalUserEdit.spGrade.setSelection(gradeList.indexOfFirst { it.id == userInfo.studentInfo?.gradeId })
    }
}