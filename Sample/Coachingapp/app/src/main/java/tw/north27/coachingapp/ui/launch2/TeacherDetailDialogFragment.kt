package tw.north27.coachingapp.ui.launch2

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.unnamed.b.atv.model.TreeNode
import com.unnamed.b.atv.view.AndroidTreeView
import com.yujie.core_lib.adapter.bindImg
import com.yujie.core_lib.base.BaseDialogFragment
import com.yujie.core_lib.ext.clicksObserve
import com.yujie.core_lib.util.ViewState
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import tw.north27.coachingapp.NavGraphLaunch2Directions
import tw.north27.coachingapp.R
import tw.north27.coachingapp.adapter.bindChartComment
import tw.north27.coachingapp.adapter.bindChartReply
import tw.north27.coachingapp.adapter.bindGender
import tw.north27.coachingapp.databinding.FragmentTeacherDetailDialogBinding
import tw.north27.coachingapp.model.ClientInfo
import tw.north27.coachingapp.model.From
import tw.north27.coachingapp.model.treeNodeHolder.EducationLevelHolder
import tw.north27.coachingapp.model.treeNodeHolder.GradeHolder
import tw.north27.coachingapp.model.treeNodeHolder.SubjectHolder
import tw.north27.coachingapp.model.treeNodeHolder.UnitHolder
import tw.north27.coachingapp.viewModel.PublicViewModel

class TeacherDetailDialogFragment : BaseDialogFragment<FragmentTeacherDetailDialogBinding>(R.layout.fragment_teacher_detail_dialog) {

    override val viewBind: (View) -> FragmentTeacherDetailDialogBinding
        get() = FragmentTeacherDetailDialogBinding::bind

    private val publicVM by sharedViewModel<PublicViewModel>()

    private val launch2Act: Launch2Activity
        get() = act as Launch2Activity

    private val from: From
        get() = arguments?.getParcelable<From>("from")!!

    private val clientInfo: ClientInfo
        get() = arguments?.getParcelable<ClientInfo>("clientInfo")!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            val bgUrl = clientInfo.bgUrl
            if (bgUrl.isNotEmpty()) ivBg.bindImg(url = clientInfo.bgUrl)
            val avatarUrl = clientInfo.avatarUrl
            if (avatarUrl.isNotEmpty()) ivAvatar.bindImg(url = clientInfo.avatarUrl, placeRes = R.drawable.ic_baseline_account_box_24_gray, roundingRadius = 120)
            tvGender.bindGender(clientInfo)
            tvName.text = clientInfo.name
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
            tvIntroTitle.text = String.format("%s%s：", getString(R.string.about), clientInfo.name)
            tvIntro.text = clientInfo.intro
        }

        binding.ivClear.clicksObserve(owner = viewLifecycleOwner) {
            findNavController().navigateUp()
        }

        binding.btnEnter.clicksObserve(owner = viewLifecycleOwner) {
            when (from) {
                From.Specify -> {
                }
                From.Pair -> {
//                    findNavController().navigate(NavGraphLaunch2Directions.actionToFragmentAskRoom())
                }
            }
        }
    }

    private fun setField() {
        val educationList = (publicVM.educationState.value as ViewState.Data).data

        val educationLevelList = clientInfo.teacherInfo?.unitsList?.map { unit ->
            educationList.educationLevelList.find { it.id == unit.educationLevelId }
        }?.toSet()?.toList()

        val gradeList = clientInfo.teacherInfo?.unitsList?.map { unit ->
            educationList.gradeList.find { it.id == unit.gradeId }
        }?.toSet()?.toList()

        val subjectList = clientInfo.teacherInfo?.unitsList?.map { unit ->
            educationList.subjectList.find { it.id == unit.subjectId }
        }?.toSet()?.toList()

        val unitList = clientInfo.teacherInfo?.unitsList

        val treeNode = TreeNode.root()
        educationLevelList?.forEach { educationLevel ->
            val educationLevelTreeNode = TreeNode(educationLevel).setViewHolder(EducationLevelHolder(cxt))
            //
            gradeList?.filter { educationLevel?.id == it?.educationLevelId }?.forEach { grade ->
                val gradeTreeNode = TreeNode(grade).setViewHolder(GradeHolder(cxt))
                //
                subjectList?.filter { it?.gradeIdList?.any { it == grade?.id } ?: false }?.forEach { subject ->
                    val subjectTreeNode = TreeNode(subject).setViewHolder(SubjectHolder(cxt))
                    //
                    unitList?.filter { educationLevel?.id == it.educationLevelId && grade?.id == it.gradeId && subject?.id == it.subjectId }?.forEach { unit ->
                        val unitTreeNode = TreeNode(unit).setViewHolder(UnitHolder(cxt))
                        subjectTreeNode.addChild(unitTreeNode)
                    }
                    //
                    gradeTreeNode.addChild(subjectTreeNode)
                }
                //
                educationLevelTreeNode.addChild(gradeTreeNode)
            }
            //
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
}