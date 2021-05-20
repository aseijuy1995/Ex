package tw.north27.coachingapp.ui

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.yujie.basemodule.BaseAppCompatActivity
import com.yujie.prefmodule.dataStore.dataStoreUserPref
import com.yujie.prefmodule.dataStore.getAuth
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import tw.north27.coachingapp.R
import tw.north27.coachingapp.adapter.finish.BottomNavigationPagerAdapter
import tw.north27.coachingapp.databinding.ActivityLaunch2Binding
import tw.north27.coachingapp.model.result.Authority


class Launch2Activity : BaseAppCompatActivity<ActivityLaunch2Binding>(ActivityLaunch2Binding::inflate) {

    lateinit var navFragment: NavHostFragment

    lateinit var navController: NavController

    lateinit var pagerAdapter: BottomNavigationPagerAdapter

    override

    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        navFragment = (supportFragmentManager.findFragmentById(R.id.frag_container_view) as NavHostFragment)
//        navController = navFragment.navController
//
//        binding.bottomNavigationView.setupWithNavController(navController)

//        navController.addOnDestinationChangedListener { _, destination, _ ->
//            binding.bottomNavigationView.isVisible =
//                when (destination.id) {
//                    R.id.fragment_main_home,
//                    R.id.fragment_question_area,
//                    R.id.fragment_study_room,
//                    R.id.fragment_notice_center,
//                    R.id.fragment_user,
//                    -> true
//                    else
//                    -> false
//                }
//        }
        //

        lifecycleScope.launch {
            val auth = dataStoreUserPref.getAuth().first()
            pagerAdapter = BottomNavigationPagerAdapter(supportFragmentManager, lifecycle, auth)
            binding.viewPager2.adapter = pagerAdapter
            binding.viewPager2.isUserInputEnabled = false
            when (auth) {
                Authority.TEACHER.toString() -> {
                    binding.bnvLaunch2.inflateMenu(R.menu.bottom_navigation_student)
                    binding.bnvLaunch2.setOnNavigationItemSelectedListener {
                        when (it.itemId) {
                            R.id.fragment_main_home -> {
                                binding.viewPager2.currentItem = 0
                                return@setOnNavigationItemSelectedListener true
                            }
                            R.id.fragment_question_area -> {
                                binding.viewPager2.currentItem = 1
                                return@setOnNavigationItemSelectedListener true
                            }
//                            R.id.fragment_study_room -> {
//                                binding.viewPager2.currentItem = 2
//                                return@setOnNavigationItemSelectedListener true
//                            }
                            R.id.fragment_notice_center -> {
                                binding.viewPager2.currentItem = 2
                                return@setOnNavigationItemSelectedListener true
                            }
                            R.id.fragment_personal_center -> {
                                binding.viewPager2.currentItem = 3
                                return@setOnNavigationItemSelectedListener true
                            }
                        }
                        false
                    }
                }
                Authority.STUDENT.toString() -> {
                    binding.bnvLaunch2.inflateMenu(R.menu.bottom_navigation_teacher)
                    binding.bnvLaunch2.setOnNavigationItemSelectedListener {
                        when (it.itemId) {
                            R.id.fragment_question_area -> {
                                binding.viewPager2.currentItem = 0
                                return@setOnNavigationItemSelectedListener true
                            }
                            R.id.fragment_notice_center -> {
                                binding.viewPager2.currentItem = 1
                                return@setOnNavigationItemSelectedListener true
                            }
                            R.id.fragment_personal_center -> {
                                binding.viewPager2.currentItem = 2
                                return@setOnNavigationItemSelectedListener true
                            }
                        }
                        false
                    }
                }
            }

//            binding.bnvLaunch2.menu.findItem(R.id.fragment_main_home).isVisible = if (auth == Authority.STUDENT.toString()) true else if (auth == Authority.TEACHER.toString()) false else false
//            binding.bnvLaunch2.menu.findItem(R.id.fragment_question_area).isVisible = if (auth == Authority.STUDENT.toString()) true else if (auth == Authority.TEACHER.toString()) true else false
////            binding.bnvLaunch2.menu.findItem(R.id.fragment_study_room).isVisible = if (auth == Authority.STUDENT.toString()) false else if (auth == Authority.TEACHER.toString()) false else false
//            binding.bnvLaunch2.menu.findItem(R.id.fragment_notice_center).isVisible = if (auth == Authority.STUDENT.toString()) false else if (auth == Authority.TEACHER.toString()) true else false
//            binding.bnvLaunch2.menu.findItem(R.id.fragment_personal_center).isVisible = if (auth == Authority.STUDENT.toString()) true else if (auth == Authority.TEACHER.toString()) true else false

        }


    }

//    override fun onSupportNavigateUp(): Boolean {
//        return super.onSupportNavigateUp() || findNavController(R.id.frag_container_view).navigateUp()
//    }

}
