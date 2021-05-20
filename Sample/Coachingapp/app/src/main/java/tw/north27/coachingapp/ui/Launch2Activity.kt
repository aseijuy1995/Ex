package tw.north27.coachingapp.ui

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.yujie.basemodule.BaseAppCompatActivity
import tw.north27.coachingapp.R
import tw.north27.coachingapp.adapter.finish.BottomNavigationIndex
import tw.north27.coachingapp.adapter.finish.BottomNavigationPagerAdapter
import tw.north27.coachingapp.databinding.ActivityLaunch2Binding


class Launch2Activity : BaseAppCompatActivity<ActivityLaunch2Binding>(ActivityLaunch2Binding::inflate) {

    lateinit var navFragment: NavHostFragment

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
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
        binding.viewPager2.adapter = BottomNavigationPagerAdapter(supportFragmentManager, lifecycle)
        binding.viewPager2.isUserInputEnabled = false
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.fragment_main_home -> {
                    binding.viewPager2.currentItem = BottomNavigationIndex.HOME.index
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.fragment_question_area -> {
                    binding.viewPager2.currentItem = BottomNavigationIndex.Chat.index
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.fragment_study_room -> {
                    binding.viewPager2.currentItem = BottomNavigationIndex.Learn.index
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.fragment_notice_center -> {
                    binding.viewPager2.currentItem = BottomNavigationIndex.Notify.index
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.fragment_personal_center -> {
                    binding.viewPager2.currentItem = BottomNavigationIndex.User.index
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
    }

//    override fun onSupportNavigateUp(): Boolean {
//        return super.onSupportNavigateUp() || findNavController(R.id.frag_container_view).navigateUp()
//    }

}
