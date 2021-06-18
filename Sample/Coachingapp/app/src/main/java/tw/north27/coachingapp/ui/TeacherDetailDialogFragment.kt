package tw.north27.coachingapp.ui

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.github.mikephil.charting.animation.Easing
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
        binding.ivAvatar.bindImg(url = userInfo.avatarPath, roundingRadius = 5)
        setSubjectLabel()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) setResponseRatePieChart()
        //
        binding.ivClear.clicksObserve(owner = viewLifecycleOwner) {
            findNavController().navigateUp()
        }

    }

    private fun setSubjectLabel() {
        val subjectList = userInfo.teacherInfo!!.subjectList.map(Subject::text).toSet().toList()
        adapter.submitData(subjectList)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setResponseRatePieChart() {
        val pieEntryList = listOf(
            PieEntry(userInfo.teacherInfo?.replyRate?.toFloat()!!, "已回覆"),
            PieEntry(100 - userInfo.teacherInfo?.replyRate?.toFloat()!!, "未回覆")
        )
        val colorList = arrayListOf<Int>(
            cxt.getColor(R.color.green_00ba9b),
            cxt.getColor(R.color.blue_02abe2),
        )
        val pieDataSet = PieDataSet(pieEntryList, "")
        pieDataSet.apply {
            sliceSpace = 2f
            selectionShift = 5f
            colors = colorList
        }
        val pieData = PieData(pieDataSet)
        pieData.setDrawValues(true)
        binding.pcResponseRate.apply {
            setUsePercentValues(false)
            description.isEnabled = false
            rotationAngle = 90f
            isHighlightPerTapEnabled = true
            animateY(1200, Easing.EaseOutBounce)


            setEntryLabelColor(Color.BLACK)
            setEntryLabelTextSize(8f)

            holeRadius = 70f

            centerText = "${cxt.getString(R.string.reply_rate)}\n${userInfo.teacherInfo?.replyRate?.toFloat()}%"
            setCenterTextSize(12f)
            legend.isEnabled = false


            data = pieData
        }.invalidate()
    }
}