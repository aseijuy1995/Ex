package tw.north27.coachingapp.ui.launch2

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.yujie.utilmodule.UserPref
import com.yujie.utilmodule.base.BaseFragment
import com.yujie.utilmodule.util.ViewState
import org.koin.androidx.viewmodel.ext.android.viewModel
import tw.north27.coachingapp.R
import tw.north27.coachingapp.adapter.GenderAdapter
import tw.north27.coachingapp.adapter.GradeAdapter
import tw.north27.coachingapp.databinding.FragmentPersonalEditBinding
import tw.north27.coachingapp.model.Gender
import tw.north27.coachingapp.model.response.Grade
import tw.north27.coachingapp.model.ClientInfo
import tw.north27.coachingapp.ui.LoadingDialogFragment
import tw.north27.coachingapp.viewModel.PersonalViewModel
import java.text.SimpleDateFormat
import java.util.*

class PersonalEditFragment : BaseFragment<FragmentPersonalEditBinding>(R.layout.fragment_personal_edit) {

    override val viewBind: (View) -> FragmentPersonalEditBinding
        get() = FragmentPersonalEditBinding::bind

    private val launch2Act: Launch2Activity
        get() = act as Launch2Activity

    private val viewModel by viewModel<PersonalViewModel>()

    private val genderAdapter = GenderAdapter()

    private val gradeAdapter = GradeAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            itemToolbarNormal.tvTitle.text = String.format("%s%s", getString(R.string.edit), getString(R.string.personal_info))
            itemData.apply {
                itemPersonalUserEdit.apply {
                    spGender.adapter = genderAdapter
                    spGrade.adapter = gradeAdapter
                }
            }
        }

        launch2Act.publicVM.userState.observe(viewLifecycleOwner) {
            binding.itemPersonalEditLoad.root.visible = (it is ViewState.Load)
            binding.itemEmpty.root.isVisible = (it is ViewState.Empty)
            binding.itemData.root.isVisible = (it is ViewState.Data)
            binding.itemError.root.isVisible = (it is ViewState.Error)
            binding.itemNetwork.root.isVisible = (it is ViewState.Network)
            when (it) {
                is ViewState.Data -> {
                    val userInfo = it.data
                    setUiData(userInfo)
                }
            }
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

        //背景
        binding.itemData.ivBgEdit.clicksObserve(owner = viewLifecycleOwner) {

        }

        //頭貼
        binding.itemData.ivAvatarEdit.clicksObserve(owner = viewLifecycleOwner) {

        }

        binding.itemData.itemPersonalInfoEdit.btnBirthday.clicksObserve(owner = viewLifecycleOwner) {
            if (launch2Act.publicVM.userState.value is ViewState.Data) {
                val userInfo = (launch2Act.publicVM.userState.value as ViewState.Data).data
                val calendar: Calendar
                val year: Int
                val month: Int
                val day: Int
                calendar = if (userInfo.birthday != null)
                    GregorianCalendar().apply { time = SimpleDateFormat("yyyy-MM-dd").parse(binding.itemData.itemPersonalInfoEdit.btnBirthday.text.toString()) }
                else
                    Calendar.getInstance()
                year = calendar[Calendar.YEAR]
                month = calendar[Calendar.MONTH]
                day = calendar[Calendar.DAY_OF_MONTH]
                DatePickerDialog(cxt, { view, year, month, dayOfMonth ->
                    binding.itemData.itemPersonalInfoEdit.btnBirthday.text = String.format("%d-%d-%d", year, (month + 1), dayOfMonth)
                }, year, month, day).apply {
                    datePicker.maxDate = Date().time
                }.show()
            }
        }

        binding.itemData.btnEdit.clicksObserve(owner = viewLifecycleOwner) {
            if (launch2Act.publicVM.userState.value is ViewState.Data) {
                val userInfo = (launch2Act.publicVM.userState.value as ViewState.Data).data
                var isNotEmpty = false
                var emptyStr = ""
                if (binding.itemData.itemPersonalUserEdit.etName.text.toString().trim().isEmpty()) {
                    isNotEmpty = true
                    emptyStr = getString(R.string.name)
                }
                if (userInfo.auth == UserPref.Authority.STUDENT && binding.itemData.itemPersonalUserEdit.etSchool.text.toString().trim().isEmpty()) {
                    isNotEmpty = true
                    emptyStr += if (emptyStr == "") getString(R.string.school) else "、${getString(R.string.school)}"
                }
                if (isNotEmpty)
                    Snackbar.make(binding.itemData.btnEdit, "$emptyStr${getString(R.string.not_empty)}", Snackbar.LENGTH_SHORT).show()
                else {
                    when (userInfo.auth) {
                        UserPref.Authority.STUDENT -> {
                            viewModel.updateUser(
                                bgUrl = "",
                                avatarUrl = "",
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
                                bgUrl = "",
                                avatarUrl = "",
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
            }
        }
    }

    private fun setUiData(clientInfo: ClientInfo) {
        binding.itemData.apply {
            if (clientInfo.bgUrl != null && clientInfo.bgUrl.isNotEmpty())
                ivBg.bindImg(url = clientInfo.bgUrl)
            else
                ivBg.bindImg(resId = launch2Act.publicVM.personalBgRes.value)
            ivAvatar.bindImg(url = clientInfo.avatarUrl, roundingRadius = 10)
            itemPersonalUserEdit.apply {
                etName.setText(clientInfo.name)
                tvAccount.text = clientInfo.account
                tvAuth.text = when (clientInfo.auth) {
                    UserPref.Authority.STUDENT -> getString(R.string.student)
                    UserPref.Authority.TEACHER -> getString(R.string.teacher)
                    else -> getString(R.string.unknown)
                }
                llSchool.isVisible = (clientInfo.auth == UserPref.Authority.STUDENT)
                etSchool.setText(clientInfo.studentInfo?.school)
                llGrade.isVisible = (clientInfo.auth == UserPref.Authority.STUDENT)
            }
            itemPersonalIntroEdit.etName.setText(clientInfo.intro)
            itemPersonalInfoEdit.apply {
                btnBirthday.text = clientInfo.birthday?.let { SimpleDateFormat("yyyy-MM-dd", Locale.TAIWAN).format(clientInfo.birthday) } ?: getString(R.string.enter_birthday)
                etHomePhone.setText(clientInfo.homePhone)
                etCellPhone.setText(clientInfo.cellPhone)
                etEmail.setText(clientInfo.email)
            }
        }
        val genderList = launch2Act.publicVM.genderList.value
        genderAdapter.submitData(genderList)
        binding.itemData.itemPersonalUserEdit.spGender.setSelection(genderList?.indexOfFirst { it == clientInfo.gender }!!)
        if (clientInfo.auth == UserPref.Authority.STUDENT) {
            val gradeList = launch2Act.publicVM.gradeList.value
            gradeAdapter.submitData(gradeList)
            binding.itemData.itemPersonalUserEdit.spGrade.setSelection(gradeList?.indexOfFirst { it.id == clientInfo.studentInfo?.gradeId }!!)
        }
    }
}