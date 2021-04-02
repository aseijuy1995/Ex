package tw.north27.coachingapp.ui

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import tw.north27.coachingapp.R
import tw.north27.coachingapp.base.BaseAppCompatActivity
import tw.north27.coachingapp.databinding.ActivityCoachingBinding


class CoachingActivity : BaseAppCompatActivity<ActivityCoachingBinding>(ActivityCoachingBinding::inflate) {

    lateinit var navFragment: NavHostFragment

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (supportFragmentManager.findFragmentById(R.id.frag_container_view) as NavHostFragment).also {
            navFragment = it
            navController = it.navController
        }

        binding.bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bottomNavigationView.isVisible = when (destination.id) {
                R.id.fragment_home,
                R.id.fragment_chat,
                R.id.fragment_learn,
                R.id.fragment_notify,
                R.id.fragment_user,
                R.id.fragment_exit_dialog
                -> true
                else -> false
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean = findNavController(R.id.frag_container_view).navigateUp()

}


//private val homeFragment = HomeFragment()
//
//private val chatFragment = ChatFragment()
//
//private val learnFragment = LearnFragment()
//
//private val notifyFragment = NotifyFragment()
//
//private val personCenterFragment = PersonCenterFragment()
//
//var activeFragment: Fragment = homeFragment
//
//fragManager.beginTransaction().add(R.id.frag_container_view, homeFragment, homeFragment.tag).hide(homeFragment).commit()
//fragManager.beginTransaction().add(R.id.frag_container_view, chatFragment, chatFragment.tag).hide(chatFragment).commit()
//fragManager.beginTransaction().add(R.id.frag_container_view, learnFragment, learnFragment.tag).hide(learnFragment).commit()
//fragManager.beginTransaction().add(R.id.frag_container_view, notifyFragment, notifyFragment.tag).hide(notifyFragment).commit()
//fragManager.beginTransaction().add(R.id.frag_container_view, personCenterFragment, personCenterFragment.tag).hide(personCenterFragment).commit()
//
//binding.bottomNavigationView.setOnNavigationItemSelectedListener {
//    itemSelectedListener(it)
//}
//binding.bottomNavigationView.setOnNavigationItemReselectedListener {
//    itemSelectedListener(it)
//}
//
//
//
//
//private fun itemSelectedListener(it: MenuItem): Boolean {
//    when (it.itemId) {
//        R.id.fragment_home -> {
//            fragManager.beginTransaction().hide(activeFragment).show(homeFragment).commit()
//            activeFragment = homeFragment
//            return true
//        }
//        R.id.fragment_chat -> {
//            fragManager.beginTransaction().hide(activeFragment).show(chatFragment).commit()
//            activeFragment = chatFragment
//            return true
//        }
//        R.id.fragment_learn -> {
//            fragManager.beginTransaction().hide(activeFragment).show(learnFragment).commit()
//            activeFragment = learnFragment
//            return true
//        }
//        R.id.fragment_notify -> {
//            fragManager.beginTransaction().hide(activeFragment).show(notifyFragment).commit()
//            activeFragment = notifyFragment
//            return true
//        }
//        R.id.fragment_person_center -> {
//            fragManager.beginTransaction().hide(activeFragment).show(personCenterFragment).commit()
//            activeFragment = personCenterFragment
//            return true
//        }
//    }
//    return false
//}