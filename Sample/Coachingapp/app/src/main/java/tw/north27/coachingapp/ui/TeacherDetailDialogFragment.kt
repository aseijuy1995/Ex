package tw.north27.coachingapp.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.yujie.utilmodule.adapter.bindImg
import com.yujie.utilmodule.base.BaseDialogFragment
import com.yujie.utilmodule.ext.clicksObserve
import tw.north27.coachingapp.R
import tw.north27.coachingapp.adapter.SubjectLabelListAdapter
import tw.north27.coachingapp.databinding.FragmentTeacherDetailDialogBinding
import tw.north27.coachingapp.model.Subject
import tw.north27.coachingapp.model.UserInfo

class TeacherDetailDialogFragment : BaseDialogFragment<FragmentTeacherDetailDialogBinding>(R.layout.fragment_teacher_detail_dialog) {

    override val viewBindingFactory: (View) -> FragmentTeacherDetailDialogBinding
        get() = FragmentTeacherDetailDialogBinding::bind

    private val args by navArgs<TeacherDetailDialogFragmentArgs>()

    private val userInfo: UserInfo
        get() = args.userInfo

    private val adapter = SubjectLabelListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, com.yujie.utilmodule.R.style.DialogTheme_Radius10)
        isCancelable = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            userInfo = this@TeacherDetailDialogFragment.userInfo
            rvSubject.adapter = adapter
        }
        binding.ivAvatar.bindImg(url = userInfo.avatarPath, roundingRadius = 10)
        val subjectList = userInfo.teacherInfo!!.subjectList.map(Subject::text).toSet().toList()
        adapter.submitData(subjectList)
        //
        val pieEntryList = listOf(
            PieEntry(userInfo.teacherInfo?.responseRate?.toFloat()!!, "已回覆")
        )
        val pieDataSet = PieDataSet(pieEntryList, "已回覆")
        val pieData = PieData(pieDataSet)
        pieData.setDrawValues(true)
        binding.pcResponseRate.apply {
//            setUsePercentValues(false)
            description.isEnabled = false
//                isRotationEnabled = false
            isHighlightPerTapEnabled = true
            animateY(500)

            //
            setDrawHoleEnabled(true)
            holeRadius = 20f
            setEntryLabelTextSize(10F)
            //

            transparentCircleRadius = 10f
            data = pieData
        }.invalidate()
        //
        binding.ivClear.clicksObserve(owner = viewLifecycleOwner) {
            findNavController().navigateUp()
        }

    }
}