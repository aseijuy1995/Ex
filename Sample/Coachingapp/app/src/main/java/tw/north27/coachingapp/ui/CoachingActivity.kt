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

    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp() || findNavController(R.id.frag_container_view).navigateUp()
    }

}
