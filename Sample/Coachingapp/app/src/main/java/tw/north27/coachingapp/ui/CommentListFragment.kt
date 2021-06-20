package tw.north27.coachingapp.ui

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.yujie.utilmodule.base.BaseFragment
import com.yujie.utilmodule.ext.clicksObserve
import com.yujie.utilmodule.ext.isVisible
import com.yujie.utilmodule.util.ViewState
import com.yujie.utilmodule.util.logI
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import tw.north27.coachingapp.R
import tw.north27.coachingapp.adapter.*
import tw.north27.coachingapp.databinding.FragmentCommentListBinding
import tw.north27.coachingapp.model.Education
import tw.north27.coachingapp.model.Grade
import tw.north27.coachingapp.model.Subject
import tw.north27.coachingapp.model.Unit
import tw.north27.coachingapp.viewModel.PersonalViewModel

class CommentListFragment : BaseFragment<FragmentCommentListBinding>(R.layout.fragment_comment_list) {

    override val viewBind: (View) -> FragmentCommentListBinding
        get() = FragmentCommentListBinding::bind

    private val viewModel by sharedViewModel<PersonalViewModel>()

    private val commentAdapter = CommentListAdapter()

    private val educationAdapter = EducationAdapter()

    private val gradeAdapter = GradeAdapter()

    private val subjectAdapter = SubjectAdapter()

    private val unitAdapter = UnitAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.itemToolbarNormal.apply {
            ivFilter.isVisible = true
            tvTitle.text = getString(R.string.comment_info)
        }
        binding.lifecycleOwner = viewLifecycleOwner
        binding.rvComment.adapter = commentAdapter
        binding.itemDrawerLayoutComment.spEducation.adapter = educationAdapter
        binding.itemDrawerLayoutComment.spGrade.adapter = gradeAdapter
        binding.itemDrawerLayoutComment.spSubject.adapter = subjectAdapter
        binding.itemDrawerLayoutComment.spUnit.adapter = unitAdapter

        viewModel.commentListState.observe(viewLifecycleOwner) {
            binding.itemShimmer.sflView.isVisible = (it is ViewState.Load)
            binding.itemEmpty.root.isVisible = (it is ViewState.Empty)
            binding.rvComment.isVisible = (it is ViewState.Data)
            binding.itemError.root.isVisible = (it is ViewState.Error)
            binding.itemNetwork.root.isVisible = (it is ViewState.Network)
            if (it !is ViewState.Initial && it !is ViewState.Load)
                binding.srlView.finishRefresh()
            when (it) {
                is ViewState.Data -> {
                    val commentList = it.data
                    commentAdapter.submitList(commentList)
                }
                //FIXME　整合處理各頁面錯誤
                is ViewState.Error, is ViewState.Network -> {
                }
            }
        }

        viewModel.educationListState.observe(viewLifecycleOwner) {
            if (it is ViewState.Empty)
                educationAdapter.submitData(listOf(viewModel.defaultEducation))
            else if (it is ViewState.Data) {
                val educationList = it.data.toMutableList()
                educationList.add(0, viewModel.defaultEducation)
                educationAdapter.submitData(educationList)
            }
            if (it is ViewState.Empty || it is ViewState.Data)
                binding.itemDrawerLayoutComment.spEducation.setSelection(0)
        }
        viewModel.gradeListState.observe(viewLifecycleOwner) {
            if (it is ViewState.Empty)
                gradeAdapter.submitData(listOf(viewModel.defaultGradle))
            else if (it is ViewState.Data) {
                val gradleList = it.data.toMutableList()
                gradleList.add(0, viewModel.defaultGradle)
                gradeAdapter.submitData(gradleList)
            }
            if (it is ViewState.Empty || it is ViewState.Data)
                binding.itemDrawerLayoutComment.spGrade.setSelection(0)
        }
        viewModel.subjectListState.observe(viewLifecycleOwner) {
            if (it is ViewState.Empty)
                subjectAdapter.submitData(listOf(viewModel.defaultSubject))
            else if (it is ViewState.Data) {
                val subjectList = it.data.toMutableList()
                subjectList.add(0, viewModel.defaultSubject)
                subjectAdapter.submitData(subjectList)
            }
            if (it is ViewState.Empty || it is ViewState.Data)
                binding.itemDrawerLayoutComment.spSubject.setSelection(0)
        }
        viewModel.unitListState.observe(viewLifecycleOwner) {
            if (it is ViewState.Empty)
                unitAdapter.submitData(listOf(viewModel.defaultUnit))
            else if (it is ViewState.Data) {
                val unitList = it.data.toMutableList()
                unitList.add(0, viewModel.defaultUnit)
                unitAdapter.submitData(unitList)
            }
            if (it is ViewState.Empty || it is ViewState.Data)
                binding.itemDrawerLayoutComment.spUnit.setSelection(0)
        }

        binding.itemToolbarNormal.ivBack.clicksObserve(owner = viewLifecycleOwner) {
            findNavController().navigateUp()
        }

        binding.itemToolbarNormal.ivFilter.clicksObserve(owner = viewLifecycleOwner) {
            binding.drawerLayout.openDrawer(GravityCompat.END)
        }

        binding.srlView.setOnRefreshListener {
            getCommentList()
        }

        binding.itemDrawerLayoutComment.spEducation.onItemSelectedEvenIfUnchangedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.getGradeList(educationId = id)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.itemDrawerLayoutComment.spGrade.onItemSelectedEvenIfUnchangedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val education = binding.itemDrawerLayoutComment.spEducation.selectedItem as Education
                viewModel.getSubjectList(educationId = education.id, gradeId = id)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.itemDrawerLayoutComment.spSubject.onItemSelectedEvenIfUnchangedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val education = binding.itemDrawerLayoutComment.spEducation.selectedItem as Education
                val grade = binding.itemDrawerLayoutComment.spGrade.selectedItem as Grade
                viewModel.getUnitList(educationId = education.id, gradeId = grade.id, subjectId = id)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.itemDrawerLayoutComment.spUnit.onItemSelectedEvenIfUnchangedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.itemDrawerLayoutComment.btnFilter.clicksObserve(owner = viewLifecycleOwner) {
            binding.srlView.autoRefresh()
            binding.drawerLayout.closeDrawer(GravityCompat.END)
        }

        binding.itemDrawerLayoutComment.btnClear.clicksObserve(owner = viewLifecycleOwner) {
            getDefaultSelection()
        }

        binding.itemDrawerLayoutComment.btnCancel.clicksObserve(owner = viewLifecycleOwner) {
            binding.drawerLayout.closeDrawer(GravityCompat.END)
        }

    }

    private fun getDefaultSelection() {
        viewModel.getEducationList()
        viewModel.getGradeList()
        viewModel.getSubjectList()
        viewModel.getUnitList()
    }

    private fun getCommentList() {
        val education = binding.itemDrawerLayoutComment.spEducation.selectedItem as Education
        val grade = binding.itemDrawerLayoutComment.spGrade.selectedItem as Grade
        val subject = binding.itemDrawerLayoutComment.spSubject.selectedItem as Subject
        val unit = binding.itemDrawerLayoutComment.spUnit.selectedItem as Unit
        logI("grade.id = ${grade.id}, subject.id = ${subject.id}, unit.id = ${unit.id}")
        viewModel.getCommentList(
            educationId = education.id,
            gradeId = grade.id,
            subjectId = subject.id,
            unitId = unit.id,
            index = 0,
            num = 10
        )
    }
}