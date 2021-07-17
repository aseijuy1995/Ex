package tw.north27.coachingapp.ui.launch2

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
import tw.north27.coachingapp.model.ClientInfo
import tw.north27.coachingapp.model.From
import tw.north27.coachingapp.model.response.Units
import tw.north27.coachingapp.model.treeNodeHolder.EducationLevelHolder
import tw.north27.coachingapp.model.treeNodeHolder.GradeHolder
import tw.north27.coachingapp.model.treeNodeHolder.SubjectHolder
import tw.north27.coachingapp.model.treeNodeHolder.UnitHolder
import tw.north27.coachingapp.ui.LoadingDialogFragment
import tw.north27.coachingapp.viewModel.PublicViewModel
import tw.north27.coachingapp.viewModel.TeacherDetailViewModel

class TeacherDetailDialogFragment : BaseDialogFragment<FragmentTeacherDetailDialogBinding>(R.layout.fragment_teacher_detail_dialog) {

    override val viewBind: (View) -> FragmentTeacherDetailDialogBinding
        get() = FragmentTeacherDetailDialogBinding::bind

    private val publicVM by sharedViewModel<PublicViewModel>()

    private val viewModel by viewModel<TeacherDetailViewModel>()

    private val from: From
        get() = arguments?.getParcelable<From>("from")!!

    private val clientInfo: ClientInfo
        get() = arguments?.getParcelable<ClientInfo>("clientInfo")!!

    private val unit: Units?
        get() = arguments?.getParcelable<Units>("unit")

    companion object {
        val REQUEST_KEY_EXIST = "REQUEST_KEY_EXIST"

        val KEY_TEACHER_CLIENT_EXIST = "KEY_TEACHER_CLIENT_EXIST"

        val KEY_TEACHER_UNIT_EXIST = "KEY_TEACHER_UNIT_EXIST"

        val KEY_TEACHER_MSG_EXIST = "KEY_TEACHER_MSG_EXIST"
    }

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

        viewModel.askRoomResponseState.observe(viewLifecycleOwner) {
            if (it is ViewState.Load) LoadingDialogFragment.show(parentFragmentManager)
            if (it !is ViewState.Initial && it !is ViewState.Load) LoadingDialogFragment.dismiss()
            when (it) {
                is ViewState.Data -> {
                    val askRoomResponse = it.data
                    when (askRoomResponse.isExist) {
                        true -> {
                            setFragmentResult(
                                REQUEST_KEY_EXIST,
                                bundleOf(
                                    KEY_TEACHER_CLIENT_EXIST to clientInfo,
                                    KEY_TEACHER_UNIT_EXIST to unit!!,
                                    KEY_TEACHER_MSG_EXIST to askRoomResponse.msg
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
            when (from) {
                From.Specify -> {
                }
                From.Pair -> {
                    viewModel.findAskRoom(
                        otherClientId = clientInfo.id,
                        unitId = unit?.id!!
                    )
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