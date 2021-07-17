package tw.north27.coachingapp.ui.launch2

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.yujie.core_lib.adapter.bindImg
import com.yujie.core_lib.base.BaseDialogFragment
import com.yujie.core_lib.ext.clicksObserve
import tw.north27.coachingapp.R
import tw.north27.coachingapp.adapter.SubjectLabelListAdapter
import tw.north27.coachingapp.adapter.bindChartComment
import tw.north27.coachingapp.adapter.bindChartReply
import tw.north27.coachingapp.adapter.bindGender
import tw.north27.coachingapp.databinding.FragmentTeacherDetailDialogBinding
import tw.north27.coachingapp.model.ClientInfo
import tw.north27.coachingapp.model.From
import tw.north27.coachingapp.model.response.Units

class TeacherDetailDialogFragment : BaseDialogFragment<FragmentTeacherDetailDialogBinding>(R.layout.fragment_teacher_detail_dialog) {

    override val viewBind: (View) -> FragmentTeacherDetailDialogBinding
        get() = FragmentTeacherDetailDialogBinding::bind

    private val launch2Act: Launch2Activity
        get() = act as Launch2Activity

    private val from: From
        get() = arguments?.getParcelable<From>("from")!!

    private val clientInfo: ClientInfo
        get() = arguments?.getParcelable<ClientInfo>("clientInfo")!!

    private lateinit var adapter: SubjectLabelListAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = SubjectLabelListAdapter(launch2Act)
        binding.apply {
            rvSubject.adapter = adapter
            //
            val bgUrl = clientInfo.bgUrl
            if (bgUrl != null && bgUrl.isNotEmpty()) ivBg.bindImg(url = clientInfo.bgUrl)
            val avatarUrl = clientInfo.avatarUrl
            if (avatarUrl != null && avatarUrl.isNotEmpty()) ivAvatar.bindImg(url = clientInfo.avatarUrl, placeRes = R.drawable.ic_baseline_account_box_24_gray, roundingRadius = 10)
            tvGender.bindGender(clientInfo)
            tvName.text = clientInfo.name
            val subjectIdList = clientInfo.teacherInfo?.unitsList?.map(Units::subjectId)?.toSet()?.toList()
            adapter.submitData(subjectIdList)
            binding.run {
                adapter.submitData(subjectIdList)
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
            //篩選
        }
    }
}