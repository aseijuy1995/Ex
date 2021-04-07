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

//    private val homeFragment = HomeFragment()
//
//    private val chatFragment = ChatFragment()
//
//    private val learnFragment = LearnFragment()
//
//    private val notifyFragment = NotifyFragment()
//
//    private val userFragment = UserFragment()
//
//    private var frag: Fragment = homeFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navFragment = (supportFragmentManager.findFragmentById(R.id.frag_container_view) as NavHostFragment)
        navController = navFragment.navController

        binding.bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bottomNavigationView.isVisible = when (destination.id) {
                R.id.fragment_home,
                R.id.fragment_chat,
                R.id.fragment_learn,
                R.id.fragment_notify,
                R.id.fragment_user,
                -> true
                else
                -> false
            }
        }

//        fragTrans.add(R.id.frag_container_view, homeFragment).hide(homeFragment).commit()
//        fragTrans.add(R.id.frag_container_view, chatFragment).hide(chatFragment).commit()
//        fragTrans.add(R.id.frag_container_view, learnFragment).hide(learnFragment).commit()
//        fragTrans.add(R.id.frag_container_view, notifyFragment).hide(notifyFragment).commit()
//        fragTrans.add(R.id.frag_container_view, userFragment).hide(userFragment).commit()
//        binding.bottomNavigationView.setOnNavigationItemSelectedListener(::itemSelectedListener)
    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp() || findNavController(R.id.frag_container_view).navigateUp()
    }

//    fun itemSelectedListener(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.fragment_home -> {
//                frag?.let { fragTrans.hide(it).commit() }
//                fragTrans.show(homeFragment).commit()
//                frag = homeFragment
//                return true
//            }
//            R.id.fragment_chat -> {
//                frag?.let { fragTrans.hide(it).commit() }
//                fragTrans.show(chatFragment).commit()
//                frag = chatFragment
//                return true
//            }
//            R.id.fragment_learn -> {
//                frag?.let { fragTrans.hide(it).commit() }
//                fragTrans.show(learnFragment).commit()
//                frag = learnFragment
//                return true
//            }
//            R.id.fragment_notify -> {
//                frag?.let { fragTrans.hide(it).commit() }
//                fragTrans.show(notifyFragment).commit()
//                frag = notifyFragment
//                return true
//            }
//            R.id.fragment_user -> {
//                frag?.let { fragTrans.hide(it).commit() }
//                fragTrans.show(userFragment).commit()
//                frag = userFragment
//                return true
//            }
//            else -> {
//
//            }
//        }
//        return false
//    }

}
