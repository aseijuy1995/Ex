package tw.north27.coachingapp.ui

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import com.yujie.utilmodule.adapter.bindImg
import com.yujie.utilmodule.base.BaseAppCompatActivity
import tw.north27.coachingapp.R
import tw.north27.coachingapp.databinding.ActivityLaunchBinding

class LaunchActivity : BaseAppCompatActivity<ActivityLaunchBinding>(ActivityLaunchBinding::inflate) {

    private val backgroundResList = listOf<Int>(
        R.drawable.ic_launch_background1,
        R.drawable.ic_launch_background2,
        R.drawable.ic_launch_background3,
        R.drawable.ic_launch_background4,
        R.drawable.ic_launch_background5,
        R.drawable.ic_launch_background6,
        R.drawable.ic_launch_background7,
        R.drawable.ic_launch_background8,
        R.drawable.ic_launch_background9,
    )

    val backgroundRes: Int
        get() = backgroundResList[backgroundResList.indices.random()]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.ivBackground.bindImg(
            resId = backgroundRes,
            blurRadius = 10,
            blurSampling = 3
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp() || findNavController(R.id.fcv_launch).navigateUp()
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
