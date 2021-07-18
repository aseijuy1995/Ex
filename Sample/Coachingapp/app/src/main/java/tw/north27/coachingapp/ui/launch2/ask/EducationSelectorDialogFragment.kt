package tw.north27.coachingapp.ui.launch2.ask

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.yujie.core_lib.base.BaseDialogFragment
import com.yujie.core_lib.ext.clicksObserve
import com.yujie.core_lib.util.ViewState
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import tw.north27.coachingapp.NavGraphLaunch2Directions
import tw.north27.coachingapp.R
import tw.north27.coachingapp.adapter.EducationLevelAdapter
import tw.north27.coachingapp.adapter.GradeAdapter
import tw.north27.coachingapp.adapter.SubjectAdapter
import tw.north27.coachingapp.adapter.UnitTypeAdapter
import tw.north27.coachingapp.databinding.FragmentEducationSelectorDialogBinding
import tw.north27.coachingapp.model.ClientInfo
import tw.north27.coachingapp.model.response.Education
import tw.north27.coachingapp.model.response.UnitType
import tw.north27.coachingapp.model.transfer.SourceFrom
import tw.north27.coachingapp.ui.LoadingDialogFragment
import tw.north27.coachingapp.viewModel.EducationSelectorViewModel
import tw.north27.coachingapp.viewModel.PublicViewModel

class EducationSelectorDialogFragment : BaseDialogFragment<FragmentEducationSelectorDialogBinding>(R.layout.fragment_education_selector_dialog) {

    override val viewBind: (View) -> FragmentEducationSelectorDialogBinding
        get() = FragmentEducationSelectorDialogBinding::bind

    private val publicVM by sharedViewModel<PublicViewModel>()

    private val viewModel by viewModel<EducationSelectorViewModel>()

    private val educationLevelAdapter = EducationLevelAdapter()

    private val gradeAdapter = GradeAdapter()

    private val subjectAdapter = SubjectAdapter()

    private val unitTypeAdapter = UnitTypeAdapter()

    private val sourceFrom: SourceFrom
        get() = arguments?.getParcelable<SourceFrom>("sourceFrom")!!

    private val clientInfo: ClientInfo?
        get() = arguments?.getParcelable<ClientInfo>("clientInfo")

    companion object {
        val REQUEST_KEY_SELECTOR = "REQUEST_KEY_SELECTOR"

        val KEY_SELECTOR_CLIENT = "KEY_SELECTOR_CLIENT"

        val KEY_SELECTOR_UNITTYPE = "KEY_SELECTOR_UNITTYPE"

        val KEY_SELECTOR_MSG = "KEY_SELECTOR_MSG"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            when (sourceFrom) {
                SourceFrom.Specify -> {
                    tvTitle.text = getString(R.string.filter)
                    btnEnter.apply {
                        text = getString(R.string.filter)
                        background = ContextCompat.getDrawable(cxt, R.drawable.shape_solid_color_primary_corners_radius_5)
                    }
                }
                SourceFrom.Pair -> {
                    tvTitle.text = getString(R.string.quick_pair)
                    btnEnter.apply {
                        text = getString(R.string.pair)
                        background = ContextCompat.getDrawable(cxt, R.drawable.shape_solid_orange_corners_radius_5)
                    }
                }
            }
            rsEducationLevel.adapter = educationLevelAdapter
            rsGrade.adapter = gradeAdapter
            rsSubject.adapter = subjectAdapter
            rsUnitType.adapter = unitTypeAdapter
        }


        publicVM.educationState.observe(viewLifecycleOwner) {
            when (it) {
                is ViewState.Data -> {
                    val education = it.data
                    setSelection(education)
                }
            }
        }

        viewModel.askRoomResponseState.observe(viewLifecycleOwner) {
            if (it is ViewState.Load) LoadingDialogFragment.show(parentFragmentManager)
            if (it !is ViewState.Initial && it !is ViewState.Load) LoadingDialogFragment.dismiss()
            when (it) {
                is ViewState.Data -> {
                    val askRoomResponse = it.data
                    when (askRoomResponse.isExist) {
                        true -> {
                            val unitType = (binding.rsUnitType.selectedItem as UnitType)
                            val msg = askRoomResponse.msg
                            setFragmentResult(
                                REQUEST_KEY_SELECTOR,
                                bundleOf(
                                    KEY_SELECTOR_CLIENT to clientInfo,
                                    KEY_SELECTOR_UNITTYPE to unitType,
                                    KEY_SELECTOR_MSG to msg
                                )
                            )
                            findNavController().navigateUp()
                        }
                        false -> {
                            findNavController().navigate(NavGraphLaunch2Directions.actionToFragmentAskRoom(askRoomResponse.askRoom!!))
                        }
                    }
                }
            }
        }

        viewModel.teacherPairState.observe(viewLifecycleOwner) {
            if (it is ViewState.Load) LoadingDialogFragment.show(parentFragmentManager)
            if (it !is ViewState.Initial && it !is ViewState.Load) LoadingDialogFragment.dismiss()
            when (it) {
                is ViewState.Empty -> {
                    Toast.makeText(cxt, getString(R.string.not_find_teacher), Toast.LENGTH_SHORT).show()
                }
                is ViewState.Data -> {
                    val clientInfo = it.data
                    val unitType = (binding.rsUnitType.selectedItem as UnitType)
                    setFragmentResult(
                        REQUEST_KEY_SELECTOR,
                        bundleOf(
                            KEY_SELECTOR_CLIENT to clientInfo,
                            KEY_SELECTOR_UNITTYPE to unitType
                        )
                    )
                    findNavController().navigateUp()
                }
            }
        }

        binding.rsEducationLevel.onItemSelectedEvenIfUnchangedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val education = getEducation(
                    education = (publicVM.educationState.value as ViewState.Data).data,
                    unitTypeList = clientInfo?.teacherInfo?.unitTypeList
                )
                gradeAdapter.submitData(
                    mutableListOf(publicVM.defaultGradle).apply {
                        addAll(
                            if (id == -1L)
                                education.gradeList
                            else
                                education.gradeList.filter { it.educationLevelId == id }
                        )
                    }
                )
                binding.rsGrade.setSelection(0)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.rsGrade.onItemSelectedEvenIfUnchangedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val education = getEducation(
                    education = (publicVM.educationState.value as ViewState.Data).data,
                    unitTypeList = clientInfo?.teacherInfo?.unitTypeList
                )
                val educationLevelId = binding.rsEducationLevel.selectedItemId
                subjectAdapter.submitData(
                    mutableListOf(publicVM.defaultSubject).apply {
                        addAll(
                            education.subjectList.filter {
                                if (educationLevelId == -1L && id == -1L)
                                    true
                                else if (educationLevelId == -1L)
                                    it.gradeIdList.any { it == id }
                                else if (id == -1L)
                                    it.educationLevelIdList.any { it == educationLevelId }
                                else
                                    it.educationLevelIdList.any { it == educationLevelId } && it.gradeIdList.any { it == id }
                            }
                        )
                    }
                )
                binding.rsSubject.setSelection(0)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.rsSubject.onItemSelectedEvenIfUnchangedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val education = getEducation(
                    education = (publicVM.educationState.value as ViewState.Data).data,
                    unitTypeList = clientInfo?.teacherInfo?.unitTypeList
                )
                val educationLevelId = binding.rsEducationLevel.selectedItemId
                val gradeId = binding.rsGrade.selectedItemId
                unitTypeAdapter.submitData(
                    mutableListOf(publicVM.defaultUnit).apply {
                        addAll(
                            education.unitTypeList.filter {
                                if (educationLevelId == -1L && gradeId == -1L && id == -1L)
                                    true
                                else if (educationLevelId == -1L && gradeId == -1L)
                                    it.subjectId == id
                                else if (educationLevelId == -1L && id == -1L)
                                    it.gradeId == gradeId
                                else if (gradeId == -1L && id == -1L)
                                    it.educationLevelId == educationLevelId
                                else if (educationLevelId == -1L)
                                    it.gradeId == gradeId && it.subjectId == id
                                else if (gradeId == -1L)
                                    it.educationLevelId == educationLevelId && it.subjectId == id
                                else if (id == -1L)
                                    it.educationLevelId == educationLevelId && it.gradeId == gradeId
                                else
                                    it.educationLevelId == educationLevelId && it.gradeId == gradeId && it.subjectId == id
                            }
                        )
                    }
                )
                binding.rsUnitType.setSelection(0)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.ivClear.clicksObserve(owner = viewLifecycleOwner) {
            findNavController().navigateUp()
        }

        binding.btnClear.clicksObserve(owner = viewLifecycleOwner) {
            val education = getEducation(
                education = (publicVM.educationState.value as ViewState.Data).data,
                unitTypeList = clientInfo?.teacherInfo?.unitTypeList
            )
            setDfSelection(education)
        }

        binding.btnEnter.clicksObserve(owner = viewLifecycleOwner) {
            val unit = (binding.rsUnitType.selectedItem as UnitType)
            if (unit.id == -1L) {
                Toast.makeText(cxt, getString(R.string.ask_desc), Toast.LENGTH_SHORT).show()
            } else {
                when (sourceFrom) {
                    SourceFrom.Specify -> {
                        viewModel.findAskRoom(
                            otherClientId = clientInfo?.id!!,
                            unitId = unit.id
                        )
                    }
                    SourceFrom.Pair -> {
                        viewModel.fetchTeacherPair(unit.id)
                    }
                }
            }
        }

    }

    private fun setSelection(education: Education) {
        when (sourceFrom) {
            SourceFrom.Specify -> {
                setSpecifyDefSelection(education)
            }
            SourceFrom.Pair -> {
                setDfSelection(education)
            }
        }
    }

    private fun setSpecifyDefSelection(education: Education) {
        val unitTypeList = clientInfo?.teacherInfo?.unitTypeList!!
        val education = getEducation(education, unitTypeList)
        setDfSelection(education)
    }

    private fun getEducation(education: Education, unitTypeList: List<UnitType>?): Education {
        when (sourceFrom) {
            SourceFrom.Specify -> {
                val education = education.copy(
                    educationLevelList = education.educationLevelList.filter { educationLevel -> unitTypeList!!.any { educationLevel.id == it.educationLevelId } },
                    gradeList = education.gradeList.filter { grade -> unitTypeList!!.any { grade.id == it.gradeId } },
                    subjectList = education.subjectList.filter { subject -> unitTypeList!!.any { subject.id == it.subjectId } },
                    unitTypeList = education.unitTypeList.filter { unitType -> unitTypeList!!.any { unitType.id == it.id } },
                )
                return education
            }
            SourceFrom.Pair -> {
                return education
            }
        }
    }

    //確認初始數據
    private fun setDfSelection(educationData: Education) {
        educationLevelAdapter.submitData(mutableListOf(publicVM.defaultEducation).apply { addAll(educationData.educationLevelList) })
        gradeAdapter.submitData(mutableListOf(publicVM.defaultGradle).apply { addAll(educationData.gradeList) })
        subjectAdapter.submitData(mutableListOf(publicVM.defaultSubject).apply { addAll(educationData.subjectList) })
        unitTypeAdapter.submitData(mutableListOf(publicVM.defaultUnit).apply { addAll(educationData.unitTypeList) })
        binding.rsEducationLevel.setSelection(0)
        binding.rsGrade.setSelection(0)
        binding.rsSubject.setSelection(0)
        binding.rsUnitType.setSelection(0)
    }

}