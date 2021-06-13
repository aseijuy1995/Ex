package tw.north27.coachingapp.ui2

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.yujie.utilmodule.UserPref
import com.yujie.utilmodule.base.BaseAppCompatActivity
import com.yujie.utilmodule.pref.getAuth
import com.yujie.utilmodule.pref.userPref
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import tw.north27.coachingapp.R
import tw.north27.coachingapp.databinding.ActivityLaunch2Binding


class Launch2Activity : BaseAppCompatActivity<ActivityLaunch2Binding>(ActivityLaunch2Binding::inflate) {

    lateinit var navFragment: NavHostFragment

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navFragment = (supportFragmentManager.findFragmentById(R.id.fcv_launch2) as NavHostFragment)
        navController = navFragment.navController
        binding.bnvLaunch2.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bnvLaunch2.isVisible = when (destination.id) {
                R.id.fragment_main_home,
                R.id.fragment_question_area,
                R.id.fragment_study_room,
                R.id.fragment_notice_center,
                R.id.fragment_personal_center,
                R.id.fragment_teacher_detail_dialog -> true
                else -> false
            }
        }

        lifecycleScope.launch {
            val auth = userPref.getAuth().first()
            binding.bnvLaunch2.menu.findItem(R.id.fragment_main_home).isVisible =
                when (auth) {
                    UserPref.Authority.STUDENT -> true
                    UserPref.Authority.TEACHER -> false
                    else -> false
                }
            binding.bnvLaunch2.menu.findItem(R.id.fragment_question_area).isVisible =
                when (auth) {
                    UserPref.Authority.STUDENT -> true
                    UserPref.Authority.TEACHER -> true
                    else -> false
                }
            binding.bnvLaunch2.menu.findItem(R.id.fragment_study_room).isVisible =
                when (auth) {
                    UserPref.Authority.STUDENT -> true
                    UserPref.Authority.TEACHER -> false
                    else -> false
                }
            binding.bnvLaunch2.menu.findItem(R.id.fragment_notice_center).isVisible =
                when (auth) {
                    UserPref.Authority.STUDENT -> true
                    UserPref.Authority.TEACHER -> true
                    else -> false
                }
            binding.bnvLaunch2.menu.findItem(R.id.fragment_personal_center).isVisible =
                when (auth) {
                    UserPref.Authority.STUDENT -> true
                    UserPref.Authority.TEACHER -> true
                    else -> false
                }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp() || findNavController(R.id.fcv_launch2).navigateUp()
    }

}
