package tw.north27.coachingapp.ui

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import tw.north27.coachingapp.R
import tw.north27.coachingapp.base.view.BaseAppCompatActivity
import tw.north27.coachingapp.databinding.ActivityCoachingBinding
import tw.north27.coachingapp.ext.viewBinding

class CoachingActivity : BaseAppCompatActivity() {

    protected val binding by viewBinding(ActivityCoachingBinding::inflate)

    lateinit var navFragment: NavHostFragment

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        navFragment = supportFragmentManager.findFragmentById(R.id.frag_container_view) as NavHostFragment
        navController = navFragment.navController

//        navController.addOnDestinationChangedListener { _, destination, _ ->
//            binding.animBottomBar.isVisible = when (destination.id) {
//                R.id.fragment_home,
//                R.id.fragment_chat_list,
//                R.id.fragment_learn,
//                R.id.fragment_notify,
//                R.id.fragment_member -> true
//                else -> false
//            }
//        }
//
        binding.animBottomBar.setupWithNavController(navController)

    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.bottom_nav, menu)
//        binding.animBottomBar.setupWithNavController(menu!!, navController)
//        return false
//    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        val navController = findNavController(R.id.frag_container_view)
//        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
//    }

    override fun onSupportNavigateUp(): Boolean = findNavController(R.id.frag_container_view).navigateUp()
}