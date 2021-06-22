package tw.north27.coachingapp.ui

import android.view.View
import com.yujie.utilmodule.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import tw.north27.coachingapp.R
import tw.north27.coachingapp.adapter.*
import tw.north27.coachingapp.databinding.FragmentPersonalCommentBinding
import tw.north27.coachingapp.viewModel.PersonalCommentViewModel
import tw.north27.coachingapp.viewModel.PublicViewModel

class PersonalCommentFragment : BaseFragment<FragmentPersonalCommentBinding>(R.layout.fragment_personal_comment) {

    override val viewBind: (View) -> FragmentPersonalCommentBinding
        get() = FragmentPersonalCommentBinding::bind

    private val publicVM by viewModel<PublicViewModel>()

    private val viewModel by viewModel<PersonalCommentViewModel>()

    private val commentAdapter = CommentListAdapter()

    private val scoreAdapter = ScoreAdapter()

    private val educationAdapter = EducationAdapter()

    private val gradeAdapter = GradeAdapter()

    private val subjectAdapter = SubjectAdapter()

    private val unitAdapter = UnitAdapter()

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        binding.apply {
//            itemToolbarNormal.apply {
//                tvTitle.text = getString(R.string.comment_info)
//                ivFilter.isVisible = true
//            }
//            rvComment.adapter = commentAdapter
//            itemDrawerLayoutComment.apply {
//                spScore.adapter = scoreAdapter
//                spEducation.adapter = educationAdapter
//                spGrade.adapter = gradeAdapter
//                spSubject.adapter = subjectAdapter
//                spUnit.adapter = unitAdapter
//            }
//        }
//        getDefaultSelection()
//        binding.srlView.autoRefresh()
//
//        publicVM.educationListState.observe(viewLifecycleOwner) {
//            if (it is ViewState.Load) LoadingDialogFragment.show(parentFragmentManager)
//            if (it !is ViewState.Initial && it !is ViewState.Load) LoadingDialogFragment.dismiss()
//            if (it is ViewState.Empty)
//                educationAdapter.submitData(listOf(publicVM.defaultEducation))
//            else if (it is ViewState.Data) {
//                educationAdapter.submitData(it.data.toMutableList().apply { add(0, publicVM.defaultEducation) })
//            }
//            if (it is ViewState.Empty || it is ViewState.Data)
//                binding.itemDrawerLayoutComment.spEducation.setSelection(0)
//        }
//        publicVM.gradeListState.observe(viewLifecycleOwner) {
//            if (it is ViewState.Load) LoadingDialogFragment.show(parentFragmentManager)
//            if (it !is ViewState.Initial && it !is ViewState.Load) LoadingDialogFragment.dismiss()
//            if (it is ViewState.Empty)
//                gradeAdapter.submitData(listOf(publicVM.defaultGradle))
//            else if (it is ViewState.Data) {
//                gradeAdapter.submitData(it.data.toMutableList().apply { add(0, publicVM.defaultGradle) })
//            }
//            if (it is ViewState.Empty || it is ViewState.Data)
//                binding.itemDrawerLayoutComment.spGrade.setSelection(0)
//        }
//        publicVM.subjectListState.observe(viewLifecycleOwner) {
//            if (it is ViewState.Load) LoadingDialogFragment.show(parentFragmentManager)
//            if (it !is ViewState.Initial && it !is ViewState.Load) LoadingDialogFragment.dismiss()
//            if (it is ViewState.Empty)
//                subjectAdapter.submitData(listOf(publicVM.defaultSubject))
//            else if (it is ViewState.Data) {
//                subjectAdapter.submitData(it.data.toMutableList().apply { add(0, publicVM.defaultSubject) })
//            }
//            if (it is ViewState.Empty || it is ViewState.Data)
//                binding.itemDrawerLayoutComment.spSubject.setSelection(0)
//        }
//        publicVM.unitListState.observe(viewLifecycleOwner) {
//            if (it is ViewState.Load) LoadingDialogFragment.show(parentFragmentManager)
//            if (it !is ViewState.Initial && it !is ViewState.Load) LoadingDialogFragment.dismiss()
//            if (it is ViewState.Empty)
//                unitAdapter.submitData(listOf(publicVM.defaultUnit))
//            else if (it is ViewState.Data) {
//                unitAdapter.submitData(it.data.toMutableList().apply { add(0, publicVM.defaultUnit) })
//            }
//            if (it is ViewState.Empty || it is ViewState.Data)
//                binding.itemDrawerLayoutComment.spUnit.setSelection(0)
//        }
//
//        viewModel.commentListState.observe(viewLifecycleOwner) {
//            binding.itemCommentListLoad.sflView.isVisible = (it is ViewState.Load)
//            binding.itemEmpty.root.isVisible = (it is ViewState.Empty)
//            binding.rvComment.isVisible = (it is ViewState.Data)
//            binding.itemError.root.isVisible = (it is ViewState.Error)
//            binding.itemNetwork.root.isVisible = (it is ViewState.Network)
//            if (it !is ViewState.Initial && it !is ViewState.Load)
//                binding.srlView.finishRefresh()
//            when (it) {
//                is ViewState.Data -> {
//                    val commentList = it.data
//                    commentAdapter.apply {
//                        this.educationList = educationList
//                        this.gradeList = gradeList
//                        this.subjectList = subjectList
//                        this.unitsList = unitsList
//                    }.submitList(commentList)
//                }
//                //FIXME　整合處理各頁面錯誤
//                is ViewState.Error, is ViewState.Network -> {
//                }
//            }
//        }
//
//        binding.itemToolbarNormal.ivBack.clicksObserve(owner = viewLifecycleOwner) {
//            findNavController().navigateUp()
//        }
//
//        binding.itemToolbarNormal.ivFilter.clicksObserve(owner = viewLifecycleOwner) {
//            binding.drawerLayout.openDrawer(GravityCompat.END)
//        }
//
//        binding.srlView.setOnRefreshListener {
//            val score = if (binding.itemDrawerLayoutComment.spScore.selectedItem is Double) (binding.itemDrawerLayoutComment.spScore.selectedItem as Double) else null
//            val education = if (binding.itemDrawerLayoutComment.spEducation.selectedItem is Education) (binding.itemDrawerLayoutComment.spEducation.selectedItem as Education) else null
//            val grade = if (binding.itemDrawerLayoutComment.spGrade.selectedItem is Grade) (binding.itemDrawerLayoutComment.spGrade.selectedItem as Grade) else null
//            val subject = if (binding.itemDrawerLayoutComment.spSubject.selectedItem is Subject) (binding.itemDrawerLayoutComment.spSubject.selectedItem as Subject) else null
//            val unit = if (binding.itemDrawerLayoutComment.spUnit.selectedItem is Units) (binding.itemDrawerLayoutComment.spUnit.selectedItem as Units) else null
//            getCommentList(score = score, education = education, grade = grade, subject = subject, units = unit, index = 0, num = 10)
//        }
//
//        binding.itemDrawerLayoutComment.spEducation.onItemSelectedEvenIfUnchangedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                lifecycleScope.launch(Dispatchers.Main) {
//                    publicVM.getGradeList(educationId = id)
//                }
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//            }
//        }
//
//        binding.itemDrawerLayoutComment.spGrade.onItemSelectedEvenIfUnchangedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                val education = binding.itemDrawerLayoutComment.spEducation.selectedItem as Education
//                lifecycleScope.launch(Dispatchers.Main) {
//                    publicVM.getSubjectList(educationId = education.id, gradeId = id)
//                }
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//            }
//        }
//
//        binding.itemDrawerLayoutComment.spSubject.onItemSelectedEvenIfUnchangedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                val education = binding.itemDrawerLayoutComment.spEducation.selectedItem as Education
//                val grade = binding.itemDrawerLayoutComment.spGrade.selectedItem as Grade
//                lifecycleScope.launch(Dispatchers.Main) {
//                    publicVM.getUnitList(educationId = education.id, gradeId = grade.id, subjectId = id)
//                }
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//            }
//        }
//
//        binding.itemDrawerLayoutComment.btnFilter.clicksObserve(owner = viewLifecycleOwner) {
//            binding.srlView.autoRefresh()
//            binding.drawerLayout.closeDrawer(GravityCompat.END)
//        }
//
//        binding.itemDrawerLayoutComment.btnClear.clicksObserve(owner = viewLifecycleOwner) {
//            getDefaultSelection()
//        }
//
//        binding.itemDrawerLayoutComment.btnCancel.clicksObserve(owner = viewLifecycleOwner) {
//            binding.drawerLayout.closeDrawer(GravityCompat.END)
//        }
//
//    }
//
//    private fun getDefaultSelection() = lifecycleScope.launch(Dispatchers.Main) {
//        val scoreListDef = async { publicVM.scoreList.value }
//        val educationListDef = async { publicVM.getEducationList() }
//        val gradeListDef = async { publicVM.getGradeList() }
//        val subjectListDef = async { publicVM.getSubjectList() }
//        val unitListDef = async { publicVM.getUnitList() }
//        setUiData(
//            scoreListDef.await(),
//            (educationListDef.await() as ViewState.Data).data,
//            (gradeListDef.await() as ViewState.Data).data,
//            (subjectListDef.await() as ViewState.Data).data,
////            (unitListDef.await() as ViewState.Data).data,
//        )
//    }
//
//    private fun setUiData(scoreList: List<Double>?, educationList: List<Education>, gradeList: List<Grade>, subjectList: List<Subject>, unitsList: List<Units>) {
//        scoreAdapter.submitData(scoreList?.toMutableList()?.apply { add(0, publicVM.defaultScore) })
//        binding.itemDrawerLayoutComment.spScore.setSelection(0)
//        educationAdapter.submitData(educationList.toMutableList().apply { add(0, publicVM.defaultEducation) })
//        binding.itemDrawerLayoutComment.spEducation.setSelection(0)
//        gradeAdapter.submitData(gradeList.toMutableList().apply { add(0, publicVM.defaultGradle) })
//        binding.itemDrawerLayoutComment.spGrade.setSelection(0)
//        subjectAdapter.submitData(subjectList.toMutableList().apply { add(0, publicVM.defaultSubject) })
//        binding.itemDrawerLayoutComment.spSubject.setSelection(0)
//        unitAdapter.submitData(unitsList.toMutableList().apply { add(0, publicVM.defaultUnit) })
//        binding.itemDrawerLayoutComment.spUnit.setSelection(0)
//    }
//
//    private fun getCommentList(score: Double? = null, education: Education? = null, grade: Grade? = null, subject: Subject? = null, units: Units? = null, index: Int, num: Int) {
//        logD(
//            "score = $score\n" +
//                    "education.id = ${education?.id}\n" +
//                    "grade.id = ${grade?.id}\n" +
//                    "subject.id = ${subject?.id}\n" +
//                    "unit.id = $${units?.id}\n" +
//                    "index = $index\n" +
//                    "num = $num\n"
//        )
//        lifecycleScope.launch(Dispatchers.Main) {
//            viewModel.getCommentList(
//                score = if (score == -1.0) null else score,
//                educationId = education?.id,
//                gradeId = grade?.id,
//                subjectId = subject?.id,
//                unitId = units?.id,
//                index = index,
//                num = num
//            )
//        }
//    }
}