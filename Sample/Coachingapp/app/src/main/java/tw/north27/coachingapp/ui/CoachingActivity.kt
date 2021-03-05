package tw.north27.coachingapp.ui

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import timber.log.Timber
import tw.north27.coachingapp.R
import tw.north27.coachingapp.databinding.ActivityCoachingBinding
import tw.north27.coachingapp.module.base.activity.BaseViewBindingAppCompatActivity


class CoachingActivity : BaseViewBindingAppCompatActivity<ActivityCoachingBinding>(ActivityCoachingBinding::inflate) {

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
                R.id.fragment_chat_list,
                R.id.fragment_learn,
                R.id.fragment_notify,
                R.id.fragment_person_center -> true
                else -> false
            }
        }

        //
        val intent = Intent()
        val manufacturer = android.os.Build.MANUFACTURER
        Timber.d("manufacturer = ${manufacturer}")
        when (manufacturer) {
            "xiaomi" -> intent.component = ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity")
            "oppo" -> intent.component = ComponentName("com.coloros.safecenter", "com.coloros.safecenter.permission.startup.StartupAppListActivity")
            "vivo" -> intent.component = ComponentName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.BgStartUpManagerActivity")
            "Honor" -> intent.component = ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.optimize.process.ProtectActivity")
            "htc" -> intent.component = ComponentName("com.htc.pitroad", "com.htc.pitroad.landingpage.activity.LandingPageActivity")
            "asus" -> intent.component = ComponentName("com.asus.mobilemanager", "com.asus.mobilemanager.MainActivity")
        }
        val size = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY).size
        Timber.d("size = ${size}")
        if (size > 0) {
            startActivity(intent)
        }

    }

    override fun onSupportNavigateUp(): Boolean = findNavController(R.id.frag_container_view).navigateUp()
}