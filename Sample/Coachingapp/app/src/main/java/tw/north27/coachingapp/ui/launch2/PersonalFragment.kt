package tw.north27.coachingapp.ui.launch2

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.yujie.utilmodule.UserPref
import com.yujie.utilmodule.adapter.bindImg
import com.yujie.utilmodule.base.BaseFragment
import com.yujie.utilmodule.ext.checkedChangesObserve
import com.yujie.utilmodule.ext.clicksObserve
import com.yujie.utilmodule.ext.visible
import com.yujie.utilmodule.util.ViewState
import org.koin.androidx.viewmodel.ext.android.viewModel
import tw.north27.coachingapp.R
import tw.north27.coachingapp.adapter.CommentListAdapter
import tw.north27.coachingapp.adapter.bindChartComment
import tw.north27.coachingapp.adapter.bindChartReply
import tw.north27.coachingapp.adapter.bindGender
import tw.north27.coachingapp.databinding.FragmentPersonalBinding
import tw.north27.coachingapp.model.UserInfo
import tw.north27.coachingapp.viewModel.PersonalViewModel
import java.text.SimpleDateFormat
import java.util.*

class PersonalFragment : BaseFragment<FragmentPersonalBinding>(R.layout.fragment_personal) {

    override val viewBind: (View) -> FragmentPersonalBinding
        get() = FragmentPersonalBinding::bind

    private val launch2Act: Launch2Activity
        get() = act as Launch2Activity

    private val viewModel by viewModel<PersonalViewModel>()

    private val commentAdapter = CommentListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launch2Act.doubleClickToExit()
        binding.apply {
            ctlLayout.apply {
                setCollapsedTitleTextColor(AppCompatResources.getColorStateList(cxt, R.color.white))
                setExpandedTitleTextColor(AppCompatResources.getColorStateList(cxt, R.color.white))
            }
            itemData.apply {
                itemPersonalIntro.tvIntro.isSelected = true
                itemPersonalComment.rvComment.adapter = commentAdapter
                itemPersonalAbout.apply {
                    itemReflectingIssues.apply {
                        ivIcon.bindImg(resId = R.drawable.ic_twotone_receipt_long_24_yellow)
                        tvText.text = getString(R.string.reflecting_issues)
                        ivClick.isVisible = true
                    }
                }
                itemPersonalSignOut.itemSignOut.apply {
                    ivIcon.bindImg(resId = R.drawable.ic_baseline_exit_to_app_24_red)
                    tvText.text = getString(R.string.sign_out)
                    ivClick.isVisible = true
                }
            }
        }
        launch2Act.publicVM.shareLink.observe(viewLifecycleOwner) {
            binding.itemData.itemPersonalShare.apply {
                root.isVisible = it?.isNotEmpty() ?: false
                itemLink.apply {
                    ivIcon.bindImg(resId = R.drawable.ic_twotone_link_24_gray)
                    tvText.text = getString(R.string.promotion_link)
                    ivClick.isVisible = true
                }
            }
        }
        launch2Act.publicVM.aboutCoaching.observe(viewLifecycleOwner) {
            binding.itemData.itemPersonalAbout.itemAboutCoaching.apply {
                root.isVisible = it.isNotEmpty()
                ivIcon.bindImg(resId = R.drawable.ic_twotone_ballot_24_green)
                tvText.text = getString(R.string.about_coaching)
                ivClick.isVisible = true
            }
        }
        launch2Act.publicVM.commonProblemList.observe(viewLifecycleOwner) {
            binding.itemData.itemPersonalAbout.itemCommonProblem.apply {
                root.isVisible = it?.isNotEmpty() ?: false
                ivIcon.bindImg(resId = R.drawable.ic_twotone_contact_support_24_orange)
                tvText.text = getString(R.string.common_problem)
                ivClick.isVisible = true
            }
        }
        launch2Act.publicVM.privacyPolicy.observe(viewLifecycleOwner) {
            binding.itemData.itemPersonalAbout.itemPrivacyPolicy.apply {
                root.isVisible = it?.isNotEmpty() ?: false
                ivIcon.bindImg(resId = R.drawable.ic_twotone_policy_24_red)
                tvText.text = getString(R.string.privacy_policy)
                ivClick.isVisible = true
            }
        }
        launch2Act.publicVM.contactUs.observe(viewLifecycleOwner) {
            binding.itemData.itemPersonalAbout.itemContactUs.apply {
                root.isVisible = it?.isNotEmpty() ?: false
                ivIcon.bindImg(resId = R.drawable.ic_twotone_connect_without_contact_24_blue)
                tvText.text = getString(R.string.contact_us)
                ivClick.isVisible = true
            }
        }

        launch2Act.publicVM.userState.observe(viewLifecycleOwner) {
            binding.itemPersonalLoad.sflView.visible = (it is ViewState.Load)
            binding.itemEmpty.root.isVisible = (it is ViewState.Empty)
            binding.itemData.root.isVisible = (it is ViewState.Data) && (viewModel.commentListState.value is ViewState.Data)
            binding.itemError.root.isVisible = (it is ViewState.Error)
            binding.itemNetwork.root.isVisible = (it is ViewState.Network)
            when (it) {
                is ViewState.Data -> {
                    val userInfo = it.data
                    setUiData(userInfo)
                }
            }
        }

        viewModel.commentListState.observe(viewLifecycleOwner) {
            binding.itemData.root.isVisible = (it is ViewState.Data) && (launch2Act.publicVM.userState.value is ViewState.Data)
            when (it) {
                is ViewState.Data -> {
                    binding.itemData.itemPersonalComment.apply {
                        val commentList = it.data
                        rvComment.isVisible = commentList.isNotEmpty()
                        commentAdapter.apply {
                            educationList = launch2Act.publicVM.educationList.value
                            gradeList = launch2Act.publicVM.gradeList.value
                            subjectList = launch2Act.publicVM.subjectList.value
                            unitsList = launch2Act.publicVM.unitList.value
                        }.submitList(commentList)
                    }
                }
            }
        }

        //編輯
        binding.itemData.itemPersonalUser.ivEdit.clicksObserve(owner = viewLifecycleOwner) {
            findNavController().navigate(PersonalFragmentDirections.actionFragmentPersonalToFragmentPersonalEdit())
        }
        //評論列表
        binding.itemData.itemPersonalComment.ivComment.clicksObserve(owner = viewLifecycleOwner) {
            findNavController().navigate(PersonalFragmentDirections.actionFragmentPersonalToFragmentCommentList())
        }
        //回覆列表
        binding.itemData.itemPersonalReply.ivComment.clicksObserve(owner = viewLifecycleOwner) {

        }
        //歷程
        binding.itemData.itemPersonalStudy.itemCourse.root.clicksObserve(owner = viewLifecycleOwner) {

        }
        //分析
        binding.itemData.itemPersonalStudy.itemAnalysis.root.clicksObserve(owner = viewLifecycleOwner) {

        }
        //回覆提醒
        binding.itemData.itemPersonalSetting.itemReplyRemind.scSwitch.checkedChangesObserve(owner = viewLifecycleOwner) {

        }
        //訊息提醒
        binding.itemData.itemPersonalSetting.itemMsgRemind.scSwitch.checkedChangesObserve(owner = viewLifecycleOwner) {

        }
        //推廣連結
        binding.itemData.itemPersonalShare.itemLink.root.clicksObserve(owner = viewLifecycleOwner) {
            startActivity(Intent.createChooser(Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(
                    Intent.EXTRA_TEXT, launch2Act.publicVM.shareLink.value
                )
            }, getString(R.string.coaching)))
        }
        //關於Coaching
        binding.itemData.itemPersonalAbout.itemAboutCoaching.root.clicksObserve(owner = viewLifecycleOwner) {
            findNavController().navigate(PersonalFragmentDirections.actionFragmentPersonalToFragmentAboutCoachingDialog())
        }
        //常見問題
        binding.itemData.itemPersonalAbout.itemCommonProblem.root.clicksObserve(owner = viewLifecycleOwner) {
            findNavController().navigate(PersonalFragmentDirections.actionFragmentPersonalToFragmentCommonProblem())
        }
        //隱私政策
        binding.itemData.itemPersonalAbout.itemPrivacyPolicy.root.clicksObserve(owner = viewLifecycleOwner) {
            findNavController().navigate(PersonalFragmentDirections.actionFragmentPersonalToFragmentPrivacyPolicyDialog())
        }
        //聯繫我們
        binding.itemData.itemPersonalAbout.itemContactUs.root.clicksObserve(owner = viewLifecycleOwner) {
            findNavController().navigate(PersonalFragmentDirections.actionFragmentPersonalToFragmentContactUsDialog())
        }
        //反映問題
        binding.itemData.itemPersonalAbout.itemReflectingIssues.root.clicksObserve(owner = viewLifecycleOwner) {
            findNavController().navigate(PersonalFragmentDirections.actionFragmentPersonalToFragmentReflectDialog())
        }
        //登出
        binding.itemData.itemPersonalSignOut.itemSignOut.root.clicksObserve(owner = viewLifecycleOwner) {
            findNavController().navigate(PersonalFragmentDirections.actionFragmentPersonalToFragmentSignOutDialog())
        }
        launch2Act.publicVM.fetchUser()
        viewModel.fetchCommentList(index = 0, num = 3)
    }

    private fun setUiData(userInfo: UserInfo) {
        if (userInfo.bgUrl != null && userInfo.bgUrl.isNotEmpty())
            binding.ivBg.bindImg(url = userInfo.bgUrl)
        else
            binding.ivBg.bindImg(resId = launch2Act.publicVM.personalBgRes.value)
        binding.itemData.apply {
            itemPersonalUser.apply {
                ivAvatar.bindImg(
                    url = userInfo.avatarUrl,
                    placeRes = R.drawable.ic_baseline_account_box_24_gray,
                    roundingRadius = 5
                )
                tvGender.bindGender(userInfo)
                tvName.text = userInfo.name
                tvAccount.text = String.format("%s：%s", getString(R.string.account), userInfo.account)
                tvAuth.text = String.format(
                    "%s：%s",
                    getString(R.string.authority),
                    when (userInfo.auth) {
                        UserPref.Authority.STUDENT -> getString(R.string.student)
                        UserPref.Authority.TEACHER -> getString(R.string.teacher)
                        else -> getString(R.string.unknown)
                    }
                )
                tvSchool.apply {
                    isVisible = (userInfo.auth == UserPref.Authority.STUDENT) && (userInfo.studentInfo != null) && (userInfo.studentInfo.school != null) && userInfo.studentInfo.school.isNotEmpty()
                    text = String.format("%s：%s", getString(R.string.school), userInfo.studentInfo?.school)
                }
                tvGrade.apply {
                    isVisible = (userInfo.auth == UserPref.Authority.STUDENT) && (userInfo.studentInfo != null) && (userInfo.studentInfo.gradeId != null)
                    text = String.format("%s：%s", getString(R.string.grade), launch2Act.publicVM.gradeList.value?.find { it.id == userInfo.studentInfo?.gradeId }?.name)
                }
            }
            itemPersonalIntro.apply {
                root.isVisible = (userInfo.intro != null) && userInfo.intro.isNotEmpty()
                tvIntroTitle.text = String.format("%s：", getString(R.string.intro))
                tvIntro.text = userInfo.intro
            }
            itemPersonalInfo.apply {
                root.isVisible = (userInfo.birthday != null)
                        || (userInfo.homePhone != null && userInfo.homePhone.isNotEmpty())
                        || (userInfo.cellPhone != null && userInfo.cellPhone.isNotEmpty())
                        || (userInfo.email != null && userInfo.email.isNotEmpty())
                itemBirthday.apply {
                    root.isVisible = userInfo.birthday != null
                    ivIcon.bindImg(resId = R.drawable.ic_twotone_today_24_gray)
                    userInfo.birthday?.let {
                        tvText.text = String.format("%s：%s", getString(R.string.birthday), SimpleDateFormat("yyyy-MM-dd", Locale.TAIWAN).format(it))
                    }
                }
                itemHomePlone.apply {
                    root.isVisible = (userInfo.homePhone != null) && userInfo.homePhone.isNotEmpty()
                    ivIcon.bindImg(resId = R.drawable.ic_twotone_contact_phone_24_gray)
                    tvText.text = String.format("%s：%s", getString(R.string.home_phone), userInfo.homePhone)
                }
                itemCellPlone.apply {
                    root.isVisible = (userInfo.cellPhone != null) && userInfo.cellPhone.isNotEmpty()
                    ivIcon.bindImg(resId = R.drawable.ic_baseline_smartphone_24_gray)
                    tvText.text = String.format("%s：%s", getString(R.string.cell_phone), userInfo.cellPhone)
                }
                itemEmail.apply {
                    root.isVisible = (userInfo.email != null) && userInfo.email.isNotEmpty()
                    ivIcon.bindImg(resId = R.drawable.ic_twotone_email_24_gray)
                    tvText.text = String.format("%s：%s", getString(R.string.email), userInfo.email)
                }
            }
            itemPersonalComment.apply {
                root.isVisible = (userInfo.auth == UserPref.Authority.TEACHER)
                if (userInfo.auth == UserPref.Authority.TEACHER) binding.itemData.itemPersonalComment.pcCommentScore.bindChartComment(userInfo)

                itemPersonalReply.apply {
                    root.isVisible = (userInfo.auth == UserPref.Authority.TEACHER)
                    if (userInfo.auth == UserPref.Authority.TEACHER) binding.itemData.itemPersonalReply.pcReplyRate.bindChartReply(userInfo)
                }
                itemPersonalStudy.apply {
                    root.isVisible = (userInfo.auth == UserPref.Authority.STUDENT)
                    itemCourse.apply {
                        ivIcon.bindImg(resId = R.drawable.ic_twotone_history_toggle_off_24_gray)
                        tvText.text = getString(R.string.course)
                        ivClick.isVisible = true
                    }
                    itemAnalysis.apply {
                        ivIcon.bindImg(resId = R.drawable.ic_twotone_area_chart_24_gray)
                        tvText.text = getString(R.string.analysis)
                        ivClick.isVisible = true
                    }
                }
                itemPersonalSetting.apply {
                    itemReplyRemind.apply {
                        root.isVisible = (userInfo.userConfig != null) && (userInfo.userConfig.replyNotice != null)
                        ivIcon.bindImg(resId = R.drawable.ic_twotone_quickreply_24_gray)
                        tvText.text = getString(R.string.reply_remind)
                        scSwitch.isVisible = true
                        scSwitch.isChecked = userInfo.userConfig?.replyNotice ?: true
                    }
                    itemMsgRemind.apply {
                        root.isVisible = (userInfo.userConfig != null) && (userInfo.userConfig.msgNotice != null)
                        ivIcon.bindImg(resId = R.drawable.ic_twotone_message_24_gray)
                        tvText.text = getString(R.string.msg_remind)
                        scSwitch.isVisible = true
                        scSwitch.isChecked = userInfo.userConfig?.msgNotice ?: true
                    }
                }
            }
        }
    }
}