package tw.north27.coachingapp.ui

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.yujie.basemodule.BaseAppCompatActivity
import com.yujie.prefmodule.dataStore.dataStoreUserPref
import com.yujie.prefmodule.dataStore.getAuth
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import tw.north27.coachingapp.R
import tw.north27.coachingapp.databinding.ActivityLaunch2Binding
import tw.north27.coachingapp.model.result.Authority


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
                R.id.fragment_personal_center -> true
                else -> false
            }
        }

        lifecycleScope.launch {
            val auth = dataStoreUserPref.getAuth().first()
            binding.bnvLaunch2.menu.findItem(R.id.fragment_main_home).isVisible =
                when (auth) {
                    Authority.STUDENT.toString() -> true
                    Authority.TEACHER.toString() -> false
                    else -> false
                }
            binding.bnvLaunch2.menu.findItem(R.id.fragment_question_area).isVisible =
                when (auth) {
                    Authority.STUDENT.toString() -> true
                    Authority.TEACHER.toString() -> true
                    else -> false
                }
            binding.bnvLaunch2.menu.findItem(R.id.fragment_study_room).isVisible =
                when (auth) {
                    Authority.STUDENT.toString() -> false
                    Authority.TEACHER.toString() -> false
                    else -> false
                }
            binding.bnvLaunch2.menu.findItem(R.id.fragment_notice_center).isVisible =
                when (auth) {
                    Authority.STUDENT.toString() -> true
                    Authority.TEACHER.toString() -> true
                    else -> false
                }
            binding.bnvLaunch2.menu.findItem(R.id.fragment_personal_center).isVisible =
                when (auth) {
                    Authority.STUDENT.toString() -> true
                    Authority.TEACHER.toString() -> true
                    else -> false
                }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp() || findNavController(R.id.fcv_launch2).navigateUp()
    }

}
