package tw.north27.coachingapp.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.yujie.utilmodule.UserPref
import com.yujie.utilmodule.adapter.bindImg
import com.yujie.utilmodule.base.BaseFragment
import com.yujie.utilmodule.ext.clicksObserve
import com.yujie.utilmodule.ext.isVisible
import com.yujie.utilmodule.util.ViewState
import org.koin.androidx.viewmodel.ext.android.viewModel
import tw.north27.coachingapp.R
import tw.north27.coachingapp.databinding.FragmentPersonalCenterBinding
import tw.north27.coachingapp.model.TeacherInfo
import tw.north27.coachingapp.model.UserInfo
import tw.north27.coachingapp.viewModel.PersonalCenterViewModel


class PersonalCenterFragment : BaseFragment<FragmentPersonalCenterBinding>(R.layout.fragment_personal_center) {

    override val viewBindingFactory: (View) -> FragmentPersonalCenterBinding
        get() = FragmentPersonalCenterBinding::bind

    private val viewModel by viewModel<PersonalCenterViewModel>()

    private val backgroundResList = listOf<Int>(
        R.drawable.ic_personal_center_background1,
        R.drawable.ic_personal_center_background2,
        R.drawable.ic_personal_center_background3,
        R.drawable.ic_personal_center_background4,
        R.drawable.ic_personal_center_background5,
        R.drawable.ic_personal_center_background6,
        R.drawable.ic_personal_center_background7,
        R.drawable.ic_personal_center_background8,
        R.drawable.ic_personal_center_background9
    )

    val backgroundRes: Int
        get() {
            val index = backgroundResList.indices.random()
            println("index = $index")
            return backgroundResList[index]
        }

    @SuppressLint("NewApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            ivBackground.bindImg(resId = backgroundRes)
            ctlLayout.apply {
                setCollapsedTitleTextColor(cxt.getColorStateList(R.color.white))
                setExpandedTitleTextColor(cxt.getColorStateList(R.color.white))
            }
            itemPersonalCenterStateMsg.tvStateMsg.isSelected = true
        }

        //
        viewModel.userInfoState.observe(viewLifecycleOwner) {
            binding.itemShimmer.sflView.isVisible = (it is ViewState.Load)
            binding.itemEmpty.root.isVisible = (it is ViewState.Empty)
            binding.nsvView.isVisible = (it is ViewState.Data)
            binding.itemError.root.isVisible = (it is ViewState.Error)
            binding.itemNetwork.root.isVisible = (it is ViewState.Network)
            when (it) {
//                is ViewState.Initial -> {
//                }
//                is ViewState.Load -> {
//                }
//                is ViewState.Empty -> {
//                }

                is ViewState.Data -> {
                    val userInfo = it.data
                    binding.apply {
                        this.userInfo = userInfo
                        itemPersonalCenterUser.ivAvatar.bindImg(
                            url = userInfo.avatarPath,
                            roundingRadius = 10
                        )
                    }
                    if (userInfo.auth == UserPref.Authority.TEACHER) setTeacherInfo(userInfo)


                }
                //FIXME　整合處理各頁面錯誤
                is ViewState.Error, is ViewState.Network -> {
                }
            }
        }


        //編輯
        binding.itemPersonalCenterUser.ivEdit.clicksObserve(owner = viewLifecycleOwner) {
            findNavController().navigate(PersonalCenterFragmentDirections.actionFragmentPersonalCenterToFragmentPersonalCenterEdit())
        }
        //歷程
        binding.itemPersonalCenterStudy.itemCourse.root.clicksObserve(owner = viewLifecycleOwner) {

        }
        //分析
        binding.itemPersonalCenterStudy.itemAnalysis.root.clicksObserve(owner = viewLifecycleOwner) {

        }
        //回覆提醒
        binding.itemPersonalCenterSetting.itemReplyRemind.root.clicksObserve(owner = viewLifecycleOwner) {

        }
        //訊息提醒
        binding.itemPersonalCenterSetting.itemMsgRemind.root.clicksObserve(owner = viewLifecycleOwner) {

        }
        //推廣連結
        binding.itemPersonalCenterShare.itemLink.root.clicksObserve(owner = viewLifecycleOwner) {

        }
        //關於Coaching
        binding.itemPersonalCenterAbout.itemAboutCoaching.root.clicksObserve(owner = viewLifecycleOwner) {

        }
        //常見問題
        binding.itemPersonalCenterAbout.itemCommonProblem.root.clicksObserve(owner = viewLifecycleOwner) {

        }
        //隱私政策
        binding.itemPersonalCenterAbout.itemPrivacyPolicy.root.clicksObserve(owner = viewLifecycleOwner) {

        }
        //聯繫我們
        binding.itemPersonalCenterAbout.itemContactUs.root.clicksObserve(owner = viewLifecycleOwner) {

        }
        //登出
        binding.itemPersonalCenterSignOut.itemSignOut.root.clicksObserve(owner = viewLifecycleOwner) {
            findNavController().navigate(PersonalCenterFragmentDirections.actionFragmentPersonalCenterToFragmentSignOutDialog())
        }


//        doubleClickToExit()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setTeacherInfo(userInfo: UserInfo) {
        val teacherInfo = userInfo.teacherInfo
        setScoreChart(teacherInfo, pieEntryList, userInfo)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setScoreChart(teacherInfo: TeacherInfo?, userInfo: UserInfo) {
        val pieEntryList = mutableListOf<PieEntry>()
        teacherInfo?.eachScoreList?.forEach { pieEntryList.add(PieEntry(it.count.toFloat(), "${it.grade} ${getString(R.string.star)} - ${it.count}${getString(R.string.pen)}")) }
        val colorList = arrayListOf<Int>(
            cxt.getColor(R.color.red_e50014),
            cxt.getColor(R.color.orange_f09401),
            cxt.getColor(R.color.yellow_f7cd3b),
            cxt.getColor(R.color.green_00ba9b),
            cxt.getColor(R.color.blue_02abe2),
            cxt.getColor(R.color.blue_082e81),
            cxt.getColor(R.color.purple_c792ea),
            cxt.getColor(R.color.green_00b900),
            cxt.getColor(R.color.red_ff5370),
        )
        val pieDataSet = PieDataSet(pieEntryList, "")
        pieDataSet.apply {
            sliceSpace = 2f
            selectionShift = 10f
            colors = colorList
        }
        val pieData = PieData(pieDataSet)
        pieData.apply {
            setDrawValues(true)
            setValueFormatter(PercentFormatter(binding.itemPersonalCenterTeacher.pcAvgCommectScore))
            setValueTextColor(Color.WHITE)
            setValueTextSize(10f)
        }
        binding.itemPersonalCenterTeacher.pcAvgCommectScore.apply {
            setUsePercentValues(true)
            description.isEnabled = false
            rotationAngle = 90f
            isHighlightPerTapEnabled = true
            animateY(1500, Easing.EaseInQuart)
            setDrawEntryLabels(false)
            holeRadius = 60f
            centerText = "${cxt.getString(R.string.score)}\n${userInfo.teacherInfo?.avgCommectScore?.toFloat()}%"
            setCenterTextSize(14f)

            legend.apply {
                verticalAlignment = Legend.LegendVerticalAlignment.CENTER
                horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
                orientation = Legend.LegendOrientation.VERTICAL
                setDrawInside(false)
                xOffset = 0f
                form = Legend.LegendForm.CIRCLE
            }
            data = pieData
        }.invalidate()
    }
}