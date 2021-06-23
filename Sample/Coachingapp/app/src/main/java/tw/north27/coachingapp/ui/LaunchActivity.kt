package tw.north27.coachingapp.ui

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.yujie.utilmodule.adapter.bindImg
import com.yujie.utilmodule.base.BaseAppCompatActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import tw.north27.coachingapp.R
import tw.north27.coachingapp.databinding.ActivityLaunchBinding
import tw.north27.coachingapp.viewModel.PublicViewModel

class LaunchActivity : BaseAppCompatActivity<ActivityLaunchBinding>(ActivityLaunchBinding::inflate) {

    val navFragment: NavHostFragment
        get() = (supportFragmentManager.findFragmentById(R.id.fcv_launch) as NavHostFragment)

    val navController: NavController
        get() = navFragment.navController

    private val publicVM by viewModel<PublicViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        publicVM.launchBgRes.observe(this) {
            binding.ivBg.bindImg(
                resId = it,
                blurRadius = 10,
                blurSampling = 3
            )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp() || navController.navigateUp()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowCompat.setDecorFitsSystemWindows(window, false)
            val controller = ViewCompat.getWindowInsetsController(window.decorView)
            window.insetsController?.let {
                it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
            controller?.hide(WindowInsets.Type.systemBars() or WindowInsets.Type.navigationBars())
            controller?.isAppearanceLightStatusBars = false
            controller?.isAppearanceLightNavigationBars = false
        } else {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_IMMERSIVE or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE

        }
    }
}
