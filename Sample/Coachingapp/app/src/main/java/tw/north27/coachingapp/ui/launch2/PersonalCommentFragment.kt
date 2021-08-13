package tw.north27.coachingapp.ui.launch2

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.yujie.core_lib.base.BaseFragment
import com.yujie.core_lib.ext.clicksObserve
import com.yujie.core_lib.ext.visible
import com.yujie.core_lib.util.ViewState
import org.koin.androidx.viewmodel.ext.android.viewModel
import tw.north27.coachingapp.R
import tw.north27.coachingapp.adapter.*
import tw.north27.coachingapp.adapter.education.EducationLevelAdapter
import tw.north27.coachingapp.adapter.education.GradeAdapter
import tw.north27.coachingapp.adapter.education.SubjectAdapter
import tw.north27.coachingapp.adapter.education.UnitTypeAdapter
import tw.north27.coachingapp.adapter.info.ScoreAdapter
import tw.north27.coachingapp.databinding.FragmentPersonalCommentBinding
import tw.north27.coachingapp.model.response.EducationLevel
import tw.north27.coachingapp.model.response.Grade
import tw.north27.coachingapp.model.response.Subject
import tw.north27.coachingapp.model.response.UnitType
import tw.north27.coachingapp.ui.launch2.basic.Launch2Activity
import tw.north27.coachingapp.viewModel.PersonalViewModel

class PersonalCommentFragment : BaseFragment<FragmentPersonalCommentBinding>(R.layout.fragment_personal_comment) {

    override val bind: (View) -> FragmentPersonalCommentBinding
        get() = FragmentPersonalCommentBinding::bind

    private val launch2Act: Launch2Activity
        get() = act as Launch2Activity

    private val viewModel by viewModel<PersonalViewModel>()

    private val commentAdapter = CommentListAdapter()

    private val scoreAdapter = ScoreAdapter()

    private val educationLevelAdapter = EducationLevelAdapter()

    private val gradeAdapter = GradeAdapter()

    private val subjectAdapter = SubjectAdapter()

    private val unitAdapter = UnitTypeAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            itemToolbarNormal.apply {
                tvTitle.text = getString(R.string.teaching_comment)
                ivFilter.isVisible = true
            }
            rvComment.adapter = commentAdapter
            itemDrawerLayoutComment.apply {
                spScore.adapter = scoreAdapter
                spEducation.adapter = educationLevelAdapter
                spGrade.adapter = gradeAdapter
                spSubject.adapter = subjectAdapter
                spUnit.adapter = unitAdapter
            }
        }

        viewModel.commentListState.observe(viewLifecycleOwner) {
            binding.itemCommentListLoad.root.visible = (it is ViewState.Load)
            binding.itemEmpty.root.isVisible = (it is ViewState.Empty)
            binding.rvComment.isVisible = (it is ViewState.Data)
            binding.itemError.root.isVisible = (it is ViewState.Error)
            binding.itemNetwork.root.isVisible = (it is ViewState.Network)
            if (it !is ViewState.Initial && it !is ViewState.Load)
                binding.srlView.finishRefresh()
            when (it) {
                is ViewState.Data -> {
                    val commentList = it.data
                    commentAdapter.apply {
                        this.educationLevelList = launch2Act.publicVM.educationLevelList.value
                        this.gradeList = launch2Act.publicVM.gradeList.value
                        this.subjectList = launch2Act.publicVM.subjectList.value
                        this.unitTypeList = launch2Act.publicVM.unitList.value
                    }.submitList(commentList)
                }
            }
        }

        launch2Act.publicVM.educationLevelList.observe(viewLifecycleOwner) {
            educationLevelAdapter.submitData(mutableListOf(launch2Act.publicVM.defaultEducation).apply { addAll(it) })
            binding.itemDrawerLayoutComment.spEducation.setSelection(0)
        }

        launch2Act.publicVM.gradeList.observe(viewLifecycleOwner) {
            gradeAdapter.submitData(mutableListOf(launch2Act.publicVM.defaultGradle).apply { addAll(it) })
            binding.itemDrawerLayoutComment.spGrade.setSelection(0)
        }

        launch2Act.publicVM.subjectList.observe(viewLifecycleOwner) {
            subjectAdapter.submitData(mutableListOf(launch2Act.publicVM.defaultSubject).apply { addAll(it) })
            binding.itemDrawerLayoutComment.spSubject.setSelection(0)
        }

        launch2Act.publicVM.unitList.observe(viewLifecycleOwner) {
            unitAdapter.submitData(mutableListOf(launch2Act.publicVM.defaultUnit).apply { addAll(it) })
            binding.itemDrawerLayoutComment.spUnit.setSelection(0)
        }

        binding.itemToolbarNormal.ivBack.clicksObserve(owner = viewLifecycleOwner) {
            findNavController().navigateUp()
        }

        binding.itemToolbarNormal.ivFilter.clicksObserve(owner = viewLifecycleOwner) {
            binding.drawerLayout.openDrawer(GravityCompat.END)
        }

        binding.srlView.setOnRefreshListener {
            val score = binding.itemDrawerLayoutComment.spScore.selectedItem as Double
            val educationId = (binding.itemDrawerLayoutComment.spEducation.selectedItem as EducationLevel).id
            val gradeId = (binding.itemDrawerLayoutComment.spGrade.selectedItem as Grade).id
            val subjectId = (binding.itemDrawerLayoutComment.spSubject.selectedItem as Subject).id
            val unitId = (binding.itemDrawerLayoutComment.spUnit.selectedItem as UnitType).id
            viewModel.fetchCommentList(
                score = if (score != -1.0) score else null,
                educationLevelId = if (educationId != -1L) educationId else null,
                gradeId = if (gradeId != -1L) gradeId else null,
                subjectId = if (subjectId != -1L) subjectId else null,
                unitId = if (unitId != -1L) unitId else null,
                index = 0,
                num = 20
            )
        }

        binding.itemDrawerLayoutComment.spEducation.onItemSelectedEvenIfUnchangedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                gradeAdapter.submitData(
                    mutableListOf(launch2Act.publicVM.defaultGradle).apply {
                        addAll(
                            if (id == -1L)
                                launch2Act.publicVM.gradeList.value ?: emptyList()
                            else
                                launch2Act.publicVM.gradeList.value?.filter { it.educationLevelId == id } ?: emptyList()
                        )
                    }
                )
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.itemDrawerLayoutComment.spSubject.onItemSelectedEvenIfUnchangedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                unitAdapter.submitData(
                    mutableListOf(launch2Act.publicVM.defaultUnit).apply {
                        addAll(
                            if (id == -1L)
                                launch2Act.publicVM.unitList.value ?: emptyList()
                            else
                                launch2Act.publicVM.unitList.value?.filter { it.subjectId == id } ?: emptyList()
                        )
                    }
                )
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.itemDrawerLayoutComment.btnFilter.clicksObserve(owner = viewLifecycleOwner) {
            binding.srlView.autoRefresh()
            binding.drawerLayout.closeDrawer(GravityCompat.END)
        }

        binding.itemDrawerLayoutComment.btnClear.clicksObserve(owner = viewLifecycleOwner) {
            setDfSelection()
        }

        binding.itemDrawerLayoutComment.btnCancel.clicksObserve(owner = viewLifecycleOwner) {
            binding.drawerLayout.closeDrawer(GravityCompat.END)
        }

        setDfSelection()
        binding.srlView.autoRefresh()
    }

    private fun setDfSelection() {
        scoreAdapter.submitData(mutableListOf(launch2Act.publicVM.defaultScore).apply { launch2Act.publicVM.scoreList.value?.let { addAll(it) } })
        educationLevelAdapter.submitData(mutableListOf(launch2Act.publicVM.defaultEducation).apply { launch2Act.publicVM.educationLevelList.value?.let { addAll(it) } })
        gradeAdapter.submitData(mutableListOf(launch2Act.publicVM.defaultGradle).apply { launch2Act.publicVM.gradeList.value?.let { addAll(it) } })
        subjectAdapter.submitData(mutableListOf(launch2Act.publicVM.defaultSubject).apply { launch2Act.publicVM.subjectList.value?.let { addAll(it) } })
        unitAdapter.submitData(mutableListOf(launch2Act.publicVM.defaultUnit).apply { launch2Act.publicVM.unitList.value?.let { addAll(it) } })
        binding.itemDrawerLayoutComment.spScore.setSelection(0)
        binding.itemDrawerLayoutComment.spEducation.setSelection(0)
        binding.itemDrawerLayoutComment.spGrade.setSelection(0)
        binding.itemDrawerLayoutComment.spSubject.setSelection(0)
        binding.itemDrawerLayoutComment.spUnit.setSelection(0)
    }
}