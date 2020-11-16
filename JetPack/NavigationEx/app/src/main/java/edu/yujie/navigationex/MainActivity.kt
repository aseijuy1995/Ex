package edu.yujie.navigationex

<<<<<<< HEAD
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import edu.yujie.navigationex.databinding.ActivityMainBinding
=======
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ActivityNavigator
>>>>>>> 5353c08b8648ab256f1ef3dde1fdfbf3127f072e

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
<<<<<<< HEAD
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

//        //toolbar
//        val navController = findNavController(R.id.fcv_view)
//        val appBarConfiguration = AppBarConfiguration(navController.graph)
//        binding.tbBar.setupWithNavController(navController, appBarConfiguration)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fcv_view) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph, binding.dlLayout)

        binding.ctlLayout.setupWithNavController(binding.tbBar, navController, appBarConfiguration)

        registerForContextMenu(binding.tbBar)

        binding.bnvView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.frag_second -> {
                    binding.ablLayout.visibility = View.GONE
                    binding.bnvView.visibility = View.GONE
                }
                else -> {
                    binding.ablLayout.visibility = View.VISIBLE
                    binding.bnvView.visibility = View.VISIBLE
                }
            }
        }

        binding.tvDsl.setOnClickListener {
            findNavController(R.id.fcv_view).navigate(NavGraphDirections.actionFragFirstToKotlinDslActivity())
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fcv_view)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_option, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.fcv_view)
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
=======
        setContentView(R.layout.activity_main)

    }

    override fun finish() {
        super.finish()
        ActivityNavigator.applyPopAnimationsToPendingTransition(this)
>>>>>>> 5353c08b8648ab256f1ef3dde1fdfbf3127f072e
    }
}