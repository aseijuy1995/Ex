package tw.north27.coachingapp.ui.launch2.ask

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.navigation.fragment.findNavController
import com.yujie.core_lib.base.BaseDialogFragment
import com.yujie.core_lib.ext.clicksObserve
import com.yujie.core_lib.util.ViewState
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import tw.north27.coachingapp.R
import tw.north27.coachingapp.adapter.EducationLevelAdapter
import tw.north27.coachingapp.adapter.GradeAdapter
import tw.north27.coachingapp.adapter.SubjectAdapter
import tw.north27.coachingapp.adapter.UnitAdapter
import tw.north27.coachingapp.databinding.FragmentEducationSelectorDialogBinding
import tw.north27.coachingapp.viewModel.PublicViewModel

class EducationSelectorDialogFragment : BaseDialogFragment<FragmentEducationSelectorDialogBinding>(R.layout.fragment_education_selector_dialog) {

    override val viewBind: (View) -> FragmentEducationSelectorDialogBinding
        get() = FragmentEducationSelectorDialogBinding::bind

    private val publicVM by sharedViewModel<PublicViewModel>()

    private val educationLevelAdapter = EducationLevelAdapter()

    private val gradeAdapter = GradeAdapter()

    private val subjectAdapter = SubjectAdapter()

    private val unitAdapter = UnitAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            rsEducationLevel.adapter = educationLevelAdapter
            rsGrade.adapter = gradeAdapter
            rsSubject.adapter = subjectAdapter
            rsUnit.adapter = unitAdapter
        }

        publicVM.educationState.observe(viewLifecycleOwner) {
            when (it) {
                is ViewState.Data -> {
                    val educationData = it.data
                    educationLevelAdapter.submitData(mutableListOf(publicVM.defaultEducation).apply { addAll(educationData.educationLevelList) })
                    gradeAdapter.submitData(mutableListOf(publicVM.defaultGradle).apply { addAll(educationData.gradeList) })
                    subjectAdapter.submitData(mutableListOf(publicVM.defaultSubject).apply { addAll(educationData.subjectList) })
                    unitAdapter.submitData(mutableListOf(publicVM.defaultUnit).apply { addAll(educationData.unitList) })
                    binding.rsEducationLevel.setSelection(0)
                    binding.rsGrade.setSelection(0)
                    binding.rsSubject.setSelection(0)
                    binding.rsUnit.setSelection(0)
                }
            }
        }

        binding.rsEducationLevel.onItemSelectedEvenIfUnchangedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                gradeAdapter.submitData(
                    mutableListOf(publicVM.defaultGradle).apply {
                        addAll(
                            if (id == -1L)
                                publicVM.gradeList.value ?: emptyList()
                            else
                                publicVM.gradeList.value?.filter { it.educationLevelId == id } ?: emptyList()
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
                subjectAdapter.submitData(
                    mutableListOf(publicVM.defaultSubject).apply {
                        addAll(
                            if (id == -1L)
                                publicVM.subjectList.value ?: emptyList()
                            else
                                publicVM.subjectList.value?.filter {
                                    it.gradeIdList.any { it == id }
                                } ?: emptyList()
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
                unitAdapter.submitData(
                    mutableListOf(publicVM.defaultUnit).apply {
                        addAll(
                            if (id == -1L)
                                publicVM.unitList.value ?: emptyList()
                            else
                                publicVM.unitList.value?.filter { it.subjectId == id } ?: emptyList()
                        )
                    }
                )
                binding.rsUnit.setSelection(0)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.ivClear.clicksObserve(owner = viewLifecycleOwner) {
            findNavController().navigateUp()
        }

        binding.btnEnter.clicksObserve(owner = viewLifecycleOwner) {
//            (binding.spUnit.selectedItem as Units?)
        }
    }

}