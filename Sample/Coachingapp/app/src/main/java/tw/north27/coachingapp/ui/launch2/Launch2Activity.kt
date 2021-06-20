package tw.north27.coachingapp.ui.launch2

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.yujie.utilmodule.UserPref
import com.yujie.utilmodule.base.BaseAppCompatActivity
import com.yujie.utilmodule.pref.getAuth
import com.yujie.utilmodule.pref.userPref
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import tw.north27.coachingapp.R
import tw.north27.coachingapp.databinding.ActivityLaunch2Binding

class Launch2Activity : BaseAppCompatActivity<ActivityLaunch2Binding>(ActivityLaunch2Binding::inflate) {

    val navFragment: NavHostFragment
        get() = (supportFragmentManager.findFragmentById(R.id.fcv_launch2) as NavHostFragment)

    val navController: NavController
        get() = navFragment.navController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.bnvLaunch2.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bnvLaunch2.isVisible = when (destination.id) {
                R.id.fragment_coaching,
                R.id.fragment_ask,
                R.id.fragment_study,
                R.id.fragment_notice,
                R.id.fragment_personal,
                    //
                R.id.fragment_teacher_detail_dialog,
                R.id.fragment_sign_out_dialog -> true
                else -> false
            }
        }

        lifecycleScope.launch(Dispatchers.IO) {
            val auth = userPref.getAuth().first()
            val itemCoaching = binding.bnvLaunch2.menu.findItem(R.id.fragment_coaching)
            val itemAsk = binding.bnvLaunch2.menu.findItem(R.id.fragment_ask)
            val itemStudy = binding.bnvLaunch2.menu.findItem(R.id.fragment_study)
            val itemNotice = binding.bnvLaunch2.menu.findItem(R.id.fragment_notice)
            val itemPersonal = binding.bnvLaunch2.menu.findItem(R.id.fragment_personal)
            when (auth) {
                UserPref.Authority.STUDENT -> {
                    itemCoaching.isVisible = true
                    itemAsk.isVisible = true
                    itemStudy.isVisible = true
                    itemNotice.isVisible = true
                    itemPersonal.isVisible = true

                }
                UserPref.Authority.TEACHER -> {
                    itemCoaching.isVisible = false
                    itemAsk.isVisible = true
                    itemStudy.isVisible = false
                    itemNotice.isVisible = true
                    itemPersonal.isVisible = true
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp() || navController.navigateUp()
    }

}
