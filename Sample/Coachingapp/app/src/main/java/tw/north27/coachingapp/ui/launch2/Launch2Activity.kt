package tw.north27.coachingapp.ui.launch2

import android.os.Bundle
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.*
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.yujie.core_lib.UserPref
import com.yujie.core_lib.base.BaseAppCompatActivity
import com.yujie.core_lib.pref.getAuth
import com.yujie.core_lib.pref.userPref
import com.yujie.core_lib.util.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import tw.north27.coachingapp.R
import tw.north27.coachingapp.databinding.ActivityLaunch2Binding
import tw.north27.coachingapp.viewModel.PublicViewModel

class Launch2Activity : BaseAppCompatActivity<ActivityLaunch2Binding>(ActivityLaunch2Binding::inflate) {

    val navFragment: NavHostFragment
        get() = (supportFragmentManager.findFragmentById(R.id.fcv_launch2) as NavHostFragment)

    val navController: NavController
        get() = navFragment.navController

    val publicVM by viewModel<PublicViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.bnvLaunch2.apply {
            itemIconTintList = null
            setupWithNavController(navController)
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bnvLaunch2.apply {
                itemTextColor = when (destination.id) {
                    R.id.fragment_coaching -> AppCompatResources.getColorStateList(context, R.color.red_eb4537)
                    R.id.fragment_ask -> AppCompatResources.getColorStateList(context, R.color.orange_f09801)
                    R.id.fragment_study -> AppCompatResources.getColorStateList(context, R.color.yellow_d29700)
                    R.id.fragment_notice -> AppCompatResources.getColorStateList(context, R.color.green_00ba9b)
                    R.id.fragment_personal -> AppCompatResources.getColorStateList(context, R.color.blue_4286f3)
                    else -> AppCompatResources.getColorStateList(context, R.color.gray_666666)
                }
                isVisible = when (destination.id) {
                    R.id.fragment_coaching,
                    R.id.fragment_ask,
                    R.id.fragment_study,
                    R.id.fragment_notice,
                    R.id.fragment_personal,
                        //
                    R.id.fragment_education_selector_dialog,
                        //
                    R.id.fragment_sign_out_dialog,
                    R.id.fragment_teacher_detail_dialog,
                    R.id.fragment_about_coaching_dialog,
                    R.id.fragment_privacy_policy_dialog,
                    R.id.fragment_contact_us_dialog,
                    R.id.fragment_reflect_dialog,
                    -> true
                    else -> false
                }
            }
        }

        lifecycleScope.launch(Dispatchers.IO) {
            val auth = userPref.getAuth().first()
            val itemCoaching = binding.bnvLaunch2.menu.findItem(R.id.fragment_coaching)
            val itemAsk = binding.bnvLaunch2.menu.findItem(R.id.fragment_ask)
            val itemStudy = binding.bnvLaunch2.menu.findItem(R.id.fragment_study)
            val itemNotice = binding.bnvLaunch2.menu.findItem(R.id.fragment_notice)
            val itemPersonal = binding.bnvLaunch2.menu.findItem(R.id.fragment_personal)
            withContext(Dispatchers.Main) {
                val inflater = navController.navInflater
                val graph = inflater.inflate(R.navigation.launch2_graph)
                when (auth) {
                    UserPref.Authority.STUDENT -> {
                        itemCoaching.isVisible = true
                        itemAsk.isVisible = true
                        itemStudy.isVisible = true
                        itemNotice.isVisible = true
                        itemPersonal.isVisible = true
                        graph.startDestination = R.id.fragment_coaching
                    }
                    UserPref.Authority.TEACHER -> {
                        itemCoaching.isVisible = false
                        itemAsk.isVisible = true
                        itemStudy.isVisible = false
                        itemNotice.isVisible = true
                        itemPersonal.isVisible = true
                        graph.startDestination = R.id.fragment_ask
                    }
                }
                navController.graph = graph
            }
        }

        publicVM.apply {
            educationState.observe(this@Launch2Activity) {
                when (it) {
                    is ViewState.Data -> {
                        val educationData = it.data
                        publicVM.setEducationLevelList(educationData.educationLevelList)
                        publicVM.setGradeList(educationData.gradeList)
                        publicVM.setSubjectList(educationData.subjectList)
                        publicVM.setUnitList(educationData.unitList)
                    }
                }
            }
            aboutDataState.observe(this@Launch2Activity) {
                when (it) {
                    is ViewState.Data -> {
                        val aboutData = it.data
                        publicVM.setShareLink(aboutData.shareLink)
                        publicVM.setAboutCoaching(aboutData.aboutCoaching)
                        publicVM.setCommonProblemList(aboutData.commonProblemList)
                        publicVM.setPrivacyPolicy(aboutData.privacyPolicy)
                        publicVM.setContactUs(aboutData.contactUs)
                        publicVM.setReflect(aboutData.reflectList)
                    }
                }
            }
        }

        publicVM.fetchEducation()
        publicVM.fetchPublicData()
    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp() || navController.navigateUp()
    }

//    override fun onWindowFocusChanged(hasFocus: Boolean) {
//        super.onWindowFocusChanged(hasFocus)
//        window.statusBarColor = Color.TRANSPARENT
//        window.navigationBarColor = Color.TRANSPARENT
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            WindowCompat.setDecorFitsSystemWindows(window, false)
//            val control = ViewCompat.getWindowInsetsController(window.decorView)
//            control?.apply {
//                systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
//                show(WindowInsetsCompat.Type.systemBars())
//                hide(WindowInsets.Type.navigationBars())
//            }
//        } else {
//            window.apply {
//                decorView.systemUiVisibility =
//                    View.SYSTEM_UI_FLAG_FULLSCREEN or //隱藏狀態欄
//                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or  //隱藏導航欄
//                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or //粘性沉浸模式
//                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
//                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or //內容顯示在狀態欄後面
//                            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION //內容顯示在導航欄後面
//            }
//        }
//    }

}
