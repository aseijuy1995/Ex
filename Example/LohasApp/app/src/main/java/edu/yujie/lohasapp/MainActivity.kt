package edu.yujie.lohasapp

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import edu.yujie.lohasapp.databinding.ActMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActMainBinding
    private lateinit var navHostFrag: NavHostFragment
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActMainBinding>(this, R.layout.act_main)
        navHostFrag = supportFragmentManager.findFragmentById(R.id.frag_container_view) as NavHostFragment
        navController = navHostFrag.navController
//        setupActionBarWithNavController(navController)
        supportActionBar?.hide()

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.frag_home, R.id.frag_store, R.id.frag_mall, R.id.frag_cart, R.id.frag_profile -> {
                    binding.animBottomBar.isVisible = true
                }
                else -> {
                    binding.animBottomBar.isVisible = false
                }
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bottom_nav, menu)
        binding.animBottomBar.setupWithNavController(menu!!, navController)
        return false
    }

    override fun onSupportNavigateUp(): Boolean {
        navController.navigateUp()
        return true
    }

}