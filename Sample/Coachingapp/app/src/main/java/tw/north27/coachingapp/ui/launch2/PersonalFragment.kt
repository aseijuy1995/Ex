package tw.north27.coachingapp.ui.launch2

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
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
import com.yujie.utilmodule.ext.checkedChangesObserve
import com.yujie.utilmodule.ext.clicksObserve
import com.yujie.utilmodule.util.ViewState
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import tw.north27.coachingapp.R
import tw.north27.coachingapp.adapter.CommentListAdapter
import tw.north27.coachingapp.databinding.FragmentPersonalBinding
import tw.north27.coachingapp.model.Gender
import tw.north27.coachingapp.model.UserInfo
import tw.north27.coachingapp.viewModel.PersonalViewModel
import java.text.SimpleDateFormat
import java.util.*

class PersonalFragment : BaseFragment<FragmentPersonalBinding>(R.layout.fragment_personal) {

    override val viewBind: (View) -> FragmentPersonalBinding
        get() = FragmentPersonalBinding::bind

    private val viewModel by sharedViewModel<PersonalViewModel>()

    private val commentAdapter = CommentListAdapter()

    @SuppressLint("NewApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            ivBackground.bindImg(resId = viewModel.backgroundRes.value)
            ctlLayout.apply {
                setCollapsedTitleTextColor(cxt.getColorStateList(R.color.white))
                setExpandedTitleTextColor(cxt.getColorStateList(R.color.white))
            }
            binding.itemData.apply {
                itemPersonalStateMsg.tvStateMsg.isSelected = true
                itemPersonalComment.rvComment.adapter = commentAdapter
                itemPersonalShare.itemLink.apply {
                    ivIcon.setImageResource(R.drawable.ic_twotone_link_24_gray)
                    tvText.text = getString(R.string.promotion_link)
                    ivClick.isVisible = true
                }
                itemPersonalAbout.apply {
                    itemAboutCoaching.apply {
                        ivIcon.setImageResource(R.drawable.ic_twotone_ballot_24_gray)
                        tvText.text = getString(R.string.about_coaching)
                        ivClick.isVisible = true
                    }
                    itemCommonProblem.apply {
                        ivIcon.setImageResource(R.drawable.ic_twotone_contact_support_24_gray)
                        tvText.text = getString(R.string.common_problem)
                        ivClick.isVisible = true
                    }
                    itemPrivacyPolicy.apply {
                        ivIcon.setImageResource(R.drawable.ic_twotone_privacy_tip_24_gray)
                        tvText.text = getString(R.string.privacy_policy)
                        ivClick.isVisible = true
                    }
                    itemContactUs.apply {
                        ivIcon.setImageResource(R.drawable.ic_twotone_connect_without_contact_24_gray)
                        tvText.text = getString(R.string.contact_us)
                        ivClick.isVisible = true
                    }
                }
                itemPersonalSignOut.itemSignOut.apply {
                    ivIcon.setImageResource(R.drawable.ic_baseline_exit_to_app_24_red)
                    tvText.text = getString(R.string.sign_out)
                    ivClick.isVisible = true
                }
            }
        }

        //
        viewModel.userState.observe(viewLifecycleOwner) {
            binding.itemShimmer.sflView.isVisible = (it is ViewState.Load)
            binding.itemEmpty.root.isVisible = (it is ViewState.Empty)
            binding.itemData.nsvView.isVisible = (it is ViewState.Data)
            binding.itemError.root.isVisible = (it is ViewState.Error)
            binding.itemNetwork.root.isVisible = (it is ViewState.Network)
            when (it) {
                is ViewState.Data -> {
                    val userInfo = it.data
                    setData(userInfo)
                }
                //FIXME　整合處理各頁面錯誤
                is ViewState.Error, is ViewState.Network -> {
                }
            }
        }

        viewModel.commentListState.observe(viewLifecycleOwner) {
            binding.itemData.itemPersonalComment.tvLastTitle.isVisible = (it is ViewState.Data)
            binding.itemData.itemPersonalComment.rvComment.isVisible = (it is ViewState.Data)
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


        //編輯
        binding.itemData.itemPersonalUser.ivEdit.clicksObserve(owner = viewLifecycleOwner) {
            findNavController().navigate(PersonalFragmentDirections.actionFragmentPersonalToFragmentPersonalEdit())
        }
        //歷程
        binding.itemData.itemPersonalStudy.itemCourse.root.clicksObserve(owner = viewLifecycleOwner) {

        }
        //分析
        binding.itemData.itemPersonalStudy.itemAnalysis.root.clicksObserve(owner = viewLifecycleOwner) {

        }
        //評論列表
        binding.itemData.itemPersonalComment.ivComment.clicksObserve(owner = viewLifecycleOwner) {
            findNavController().navigate(PersonalFragmentDirections.actionFragmentPersonalToFragmentCommentList())
        }
        //回覆提醒
        binding.itemData.itemPersonalSetting.itemReplyRemind.scSwitch.checkedChangesObserve(owner = viewLifecycleOwner) {

        }
        //訊息提醒
        binding.itemData.itemPersonalSetting.itemMsgRemind.scSwitch.checkedChangesObserve(owner = viewLifecycleOwner) {

        }
        //推廣連結
        binding.itemData.itemPersonalShare.itemLink.root.clicksObserve(owner = viewLifecycleOwner) {

        }
        //關於Coaching
        binding.itemData.itemPersonalAbout.itemAboutCoaching.root.clicksObserve(owner = viewLifecycleOwner) {

        }
        //常見問題
        binding.itemData.itemPersonalAbout.itemCommonProblem.root.clicksObserve(owner = viewLifecycleOwner) {

        }
        //隱私政策
        binding.itemData.itemPersonalAbout.itemPrivacyPolicy.root.clicksObserve(owner = viewLifecycleOwner) {

        }
        //聯繫我們
        binding.itemData.itemPersonalAbout.itemContactUs.root.clicksObserve(owner = viewLifecycleOwner) {

        }
        //登出
        binding.itemData.itemPersonalSignOut.itemSignOut.root.clicksObserve(owner = viewLifecycleOwner) {
            findNavController().navigate(PersonalFragmentDirections.actionFragmentPersonalToFragmentSignOutDialog())
        }
//        doubleClickToExit()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setData(userInfo: UserInfo) {
        binding.itemData.apply {
            itemPersonalUser.apply {
                ivAvatar.bindImg(
                    url = userInfo.avatarPath,
                    roundingRadius = 5
                )
                tvGender.apply {
                    @DrawableRes val imgRes: Int
                    @StringRes val strRes: Int
                    when (userInfo.gender) {
                        Gender.MALE -> {
                            imgRes = R.drawable.shape_solid_blue_corners_radius_5
                            strRes = R.string.male
                        }
                        Gender.FEMALE -> {
                            imgRes = R.drawable.shape_solid_red_corners_radius_5
                            strRes = R.string.female
                        }
                        else -> {
                            imgRes = R.drawable.shape_solid_green_corners_radius_5
                            strRes = R.string.not
                        }
                    }
                    setBackgroundResource(imgRes)
                    text = context.getString(strRes)
                }
                tvName.text = userInfo.name
                tvId.text = String.format("%s：%d", getString(R.string.id), userInfo.id)
                tvSchool.apply {
                    isVisible = (userInfo.auth == UserPref.Authority.STUDENT) && (userInfo.studentInfo != null) && userInfo.studentInfo.school.isNotEmpty()
                    text = String.format("%s：%s", getString(R.string.school), userInfo.studentInfo?.school)
                }
                tvGrade.apply {
                    isVisible = (userInfo.auth == UserPref.Authority.STUDENT) && (userInfo.studentInfo != null)
                    text = String.format("%s：%s", getString(R.string.grade), userInfo.studentInfo?.grade?.text)
                }
                tvAuth.text = String.format(
                    "%s：%s",
                    getString(R.string.authority),
                    when (userInfo.auth) {
                        UserPref.Authority.STUDENT -> getString(R.string.student)
                        UserPref.Authority.TEACHER -> getString(R.string.teacher)
                        else -> getString(R.string.unknown)
                    }
                )
            }
            itemPersonalStateMsg.apply {
                root.isVisible = (userInfo.desc != null) && userInfo.desc.isNotEmpty()
                tvStateMsgTitle.text = String.format("%s：", getString(R.string.state_msg))
                tvStateMsg.text = userInfo.desc
            }
            itemPersonalInfo.apply {
                root.isVisible = (userInfo.birthday != null)
                        || (userInfo.homePhone != null && userInfo.homePhone.isNotEmpty())
                        || (userInfo.cellPhone != null && userInfo.cellPhone.isNotEmpty())
                        || (userInfo.email != null && userInfo.email.isNotEmpty())
                itemBirthday.apply {
                    root.isVisible = userInfo.birthday != null
                    ivIcon.setImageResource(R.drawable.ic_twotone_today_24_gray)
                    userInfo.birthday?.let { tvText.text = String.format("%s：%s", getString(R.string.birthday), SimpleDateFormat("yyyy-MM-dd", Locale.TAIWAN).format(it)) }
                }
                itemHomePlone.apply {
                    root.isVisible = userInfo.homePhone != null
                    ivIcon.setImageResource(R.drawable.ic_twotone_contact_phone_24_gray)
                    tvText.text = String.format("%s：%s", getString(R.string.home_phone), userInfo.homePhone)
                }
                itemCellPlone.apply {
                    root.isVisible = userInfo.cellPhone != null
                    ivIcon.setImageResource(R.drawable.ic_baseline_smartphone_24_gray)
                    tvText.text = String.format("%s：%s", getString(R.string.cell_phone), userInfo.cellPhone)
                }
                itemEmail.apply {
                    root.isVisible = userInfo.email != null
                    ivIcon.setImageResource(R.drawable.ic_twotone_email_24_gray)
                    tvText.text = String.format("%s：%s", getString(R.string.email), userInfo.email)
                }
            }
            itemPersonalComment.apply {
                root.isVisible = (userInfo.auth == UserPref.Authority.TEACHER)
                if (userInfo.auth == UserPref.Authority.TEACHER) setCommentChart(userInfo)
            }
            itemPersonalReply.apply {
                root.isVisible = (userInfo.auth == UserPref.Authority.TEACHER)
                if (userInfo.auth == UserPref.Authority.TEACHER) setReplyRateChart(userInfo)
            }
            itemPersonalStudy.apply {
                root.isVisible = (userInfo.auth == UserPref.Authority.STUDENT)
                itemCourse.apply {
                    ivIcon.setImageResource(R.drawable.ic_twotone_history_toggle_off_24_gray)
                    tvText.text = getString(R.string.course)
                    ivClick.isVisible = true
                }
                itemAnalysis.apply {
                    ivIcon.setImageResource(R.drawable.ic_twotone_area_chart_24_gray)
                    tvText.text = getString(R.string.analysis)
                    ivClick.isVisible = true
                }

            }
            itemPersonalSetting.apply {
                itemReplyRemind.apply {
                    ivIcon.setImageResource(R.drawable.ic_twotone_quickreply_24_gray)
                    tvText.text = getString(R.string.reply_remind)
                    scSwitch.isVisible = true
                    scSwitch.isChecked = userInfo.userConfig?.replyNotice ?: false
                }
                itemMsgRemind.apply {
                    ivIcon.setImageResource(R.drawable.ic_twotone_message_24_gray)
                    tvText.text = getString(R.string.msg_remind)
                    scSwitch.isVisible = true
                    scSwitch.isChecked = userInfo.userConfig?.msgNotice ?: false
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setCommentChart(userInfo: UserInfo) {
        val teacherInfo = userInfo.teacherInfo
        val pieEntryList = mutableListOf<PieEntry>()
        teacherInfo?.eachCommentScoreInfoList?.forEach {
            pieEntryList.add(PieEntry(it.count.toFloat(), "${it.grade} ${getString(R.string.star)}：${it.count}${getString(R.string.pen)}"))
        }
        val colorList = arrayListOf<Int>(
            cxt.getColor(R.color.red_e50014),
            cxt.getColor(R.color.orange_f09401),
            cxt.getColor(R.color.yellow_f7cd3b),
            cxt.getColor(R.color.green_00ba9b),
            cxt.getColor(R.color.blue_02abe2),
            cxt.getColor(R.color.blue_082e81),
            cxt.getColor(R.color.purple_c792ea),
            cxt.getColor(R.color.red_ff5370),
            cxt.getColor(R.color.yellow_d29700),
            cxt.getColor(R.color.green_00b900),
            cxt.getColor(R.color.blue_6ca0cd),
        )
        val pieDataSet = PieDataSet(pieEntryList, null)
        pieDataSet.apply {
            sliceSpace = 2f
            selectionShift = 10f
            colors = colorList
        }
        val pieData = PieData(pieDataSet)
        pieData.apply {
            setDrawValues(true)
            setValueFormatter(PercentFormatter(binding.itemData.itemPersonalComment.pcCommentScore))
            setValueTextColor(Color.WHITE)
            setValueTextSize(10f)
        }
        binding.itemData.itemPersonalComment.pcCommentScore.apply {
            setUsePercentValues(true)
            description.isEnabled = false
            rotationAngle = 90f
            isHighlightPerTapEnabled = true
            animateY(1500, Easing.EaseInQuart)
            setDrawEntryLabels(false)
            holeRadius = 60f
            centerText = "${cxt.getString(R.string.score)}\n${userInfo.teacherInfo?.avgCommentScore}"
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

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setReplyRateChart(userInfo: UserInfo) {
        val teacherInfo = userInfo.teacherInfo!!
        val pieEntryList = listOf<PieEntry>(
            PieEntry(teacherInfo.replyNum.toFloat(), "${getString(R.string.reply_num)}：${teacherInfo.replyNum}${getString(R.string.pen)}"),
            PieEntry(teacherInfo.noReplyNum.toFloat(), "${getString(R.string.no_reply_num)}：${teacherInfo.noReplyNum}${getString(R.string.pen)}"),
        )
        val colorList = arrayListOf<Int>(
            cxt.getColor(R.color.green_00ba9b),
            cxt.getColor(R.color.blue_02abe2)
        )
        val pieDataSet = PieDataSet(pieEntryList, null)
        pieDataSet.apply {
            sliceSpace = 2f
            selectionShift = 10f
            colors = colorList
        }
        val pieData = PieData(pieDataSet)
        pieData.apply {
            setDrawValues(true)
            setValueFormatter(PercentFormatter(binding.itemData.itemPersonalReply.pcReplyRate))
            setValueTextColor(Color.WHITE)
            setValueTextSize(10f)
        }
        binding.itemData.itemPersonalReply.pcReplyRate.apply {
            setUsePercentValues(true)
            description.isEnabled = false
            rotationAngle = 90f
            isHighlightPerTapEnabled = true
            animateY(1500, Easing.EaseInQuart)
            setDrawEntryLabels(false)
            holeRadius = 70f
            centerText = "${cxt.getString(R.string.reply_rate)}\n${userInfo.teacherInfo.replyRate}%"
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