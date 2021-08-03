package tw.north27.coachingapp.ui.launch2.share

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.unnamed.b.atv.model.TreeNode
import com.unnamed.b.atv.view.AndroidTreeView
import com.yujie.core_lib.adapter.bindImg
import com.yujie.core_lib.base.BaseDialogFragment
import com.yujie.core_lib.ext.clicksObserve
import com.yujie.core_lib.util.ViewState
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import tw.north27.coachingapp.NavGraphLaunch2Directions
import tw.north27.coachingapp.R
import tw.north27.coachingapp.adapter.bindChartComment
import tw.north27.coachingapp.adapter.bindChartReply
import tw.north27.coachingapp.adapter.bindGender
import tw.north27.coachingapp.databinding.FragmentTeacherDetailDialogBinding
import tw.north27.coachingapp.model.response.ClientInfo
import tw.north27.coachingapp.model.response.UnitType
import tw.north27.coachingapp.model.transfer.SourceFrom
import tw.north27.coachingapp.model.treeNodeHolder.EducationLevelHolder
import tw.north27.coachingapp.model.treeNodeHolder.GradeHolder
import tw.north27.coachingapp.model.treeNodeHolder.SubjectHolder
import tw.north27.coachingapp.model.treeNodeHolder.UnitTypeHolder
import tw.north27.coachingapp.viewModel.PublicViewModel
import tw.north27.coachingapp.viewModel.TeacherDetailViewModel

class TeacherDetailDialogFragment : BaseDialogFragment<FragmentTeacherDetailDialogBinding>(R.layout.fragment_teacher_detail_dialog) {

    override val viewBind: (View) -> FragmentTeacherDetailDialogBinding
        get() = FragmentTeacherDetailDialogBinding::bind
    private val publicVM by sharedViewModel<PublicViewModel>()
    private val viewModel by viewModel<TeacherDetailViewModel>()
    private val sourceFrom: SourceFrom
        get() = arguments?.getParcelable<SourceFrom>("sourceFrom")!!
    private val clientInfo: ClientInfo
        get() = arguments?.getParcelable<ClientInfo>("clientInfo")!!
    private val unitType: UnitType?
        get() = arguments?.getParcelable<UnitType>("unitType")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            val bgUrl = clientInfo.bgUrl
            if (bgUrl.isNotEmpty()) ivBg.bindImg(url = clientInfo.bgUrl)
            val avatarUrl = clientInfo.avatarUrl
            if (avatarUrl.isNotEmpty()) ivAvatar.bindImg(url = clientInfo.avatarUrl, placeRes = R.drawable.ic_baseline_account_box_24_gray, roundingRadius = 10)
            tvGender.bindGender(clientInfo)
            tvName.text = clientInfo.name
            tvIntroTitle.text = String.format("%s%s", getString(R.string.about), clientInfo.name)
            tvIntro.text = clientInfo.intro
            setField()
            binding.run {
                pcCommentScore.bindChartComment(
                    clientInfo = clientInfo,
                    valueTextSize = 8f,
                    isDrawEntryLabels = true,
                    holeRadius = 40f,
                    centerTextSize = 10f,
                    isLegend = false
                )
                pcReplyRate.bindChartReply(
                    clientInfo = clientInfo,
                    valueTextSize = 8f,
                    isDrawEntryLabels = true,
                    holeRadius = 40f,
                    centerTextSize = 10f,
                    isLegend = false
                )
            }
        }

        viewModel.findAskRoomState.observe(viewLifecycleOwner) {
            if (it is ViewState.Load) LoadingDialogFragment.show(parentFragmentManager)
            if (it !is ViewState.Initial && it !is ViewState.Load) LoadingDialogFragment.dismiss()
            when (it) {
                is ViewState.Data -> {
                    val askRoomResponse = it.data
                    when (askRoomResponse.isExist) {
                        true -> {
                            setFragmentResult(
                                REQUEST_KEY_TEACHER,
                                bundleOf(
                                    KEY_TEACHER_CLIENT to clientInfo,
                                    KEY_TEACHER_UNITTYPE to unitType!!,
                                    KEY_TEACHER_MSG to askRoomResponse.msg
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


        binding.ivClear.clicksObserve(owner = viewLifecycleOwner) {
            findNavController().navigateUp()
        }

        binding.btnEnter.clicksObserve(owner = viewLifecycleOwner) {
            when (sourceFrom) {
                SourceFrom.Specify -> {
                    setFragmentResult(
                        REQUEST_KEY_TEACHER,
                        bundleOf(
                            KEY_TEACHER_CLIENT to clientInfo,
                        )
                    )
                    findNavController().navigateUp()
                }
                SourceFrom.Pair -> {
                    viewModel.findAskRoom(
                        otherClientId = clientInfo.id,
                        unitId = unitType?.id!!
                    )
                }
            }
        }
    }

    private fun setField() {
        val educationList = (publicVM.educationState.value as ViewState.Data).data

        val educationLevelList = clientInfo.teacherInfo?.unitTypeList?.map { unit ->
            educationList.educationLevelList.find { it.id == unit.educationLevelId }
        }?.toSet()?.toList()

        val gradeList = clientInfo.teacherInfo?.unitTypeList?.map { unit ->
            educationList.gradeList.find { it.id == unit.gradeId }
        }?.toSet()?.toList()

        val subjectList = clientInfo.teacherInfo?.unitTypeList?.map { unit ->
            educationList.subjectList.find { it.id == unit.subjectId }
        }?.toSet()?.toList()

        val unitList = clientInfo.teacherInfo?.unitTypeList

        val treeNode = TreeNode.root()
        educationLevelList?.forEach { educationLevel ->
            val educationLevelTreeNode = TreeNode(educationLevel).setViewHolder(EducationLevelHolder(cxt))
            gradeList?.filter { educationLevel?.id == it?.educationLevelId }?.forEach { grade ->
                val gradeTreeNode = TreeNode(grade).setViewHolder(GradeHolder(cxt))
                subjectList?.filter { it?.gradeIdList?.any { it == grade?.id } ?: false }?.forEach { subject ->
                    val subjectTreeNode = TreeNode(subject).setViewHolder(SubjectHolder(cxt))
                    unitList?.filter { educationLevel?.id == it.educationLevelId && grade?.id == it.gradeId && subject?.id == it.subjectId }?.forEach { unit ->
                        val unitTreeNode = TreeNode(unit).setViewHolder(UnitTypeHolder(cxt))
                        subjectTreeNode.addChild(unitTreeNode)
                    }
                    gradeTreeNode.addChild(subjectTreeNode)
                }
                educationLevelTreeNode.addChild(gradeTreeNode)
            }
            treeNode.addChild(educationLevelTreeNode)
        }
        val androidTreeView = AndroidTreeView(cxt, treeNode)
        androidTreeView.apply {
            setDefaultAnimation(true)
            setDefaultContainerStyle(R.style.TreeNodeStyle)
            setDefaultViewHolder(EducationLevelHolder::class.java)
        }
        binding.llField.addView(androidTreeView.view)
        androidTreeView.expandAll()
    }

    companion object {
        val REQUEST_KEY_TEACHER = "REQUEST_KEY_TEACHER"

        val KEY_TEACHER_CLIENT = "KEY_TEACHER_CLIENT"

        val KEY_TEACHER_UNITTYPE = "KEY_TEACHER_UNITTYPE"

        val KEY_TEACHER_MSG = "KEY_TEACHER_MSG"
    }
}