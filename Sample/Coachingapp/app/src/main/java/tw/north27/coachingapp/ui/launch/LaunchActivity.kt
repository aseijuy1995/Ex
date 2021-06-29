package tw.north27.coachingapp.ui.launch

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
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
                placeRes = it,
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
        window.statusBarColor = Color.TRANSPARENT
        window.navigationBarColor = Color.TRANSPARENT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowCompat.setDecorFitsSystemWindows(window, false)
            val control = ViewCompat.getWindowInsetsController(window.decorView)
            control?.apply {
                systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                show(WindowInsetsCompat.Type.systemBars())
                hide(WindowInsets.Type.navigationBars())
            }
        } else {
            window.apply {
                decorView.systemUiVisibility =
//                    View.SYSTEM_UI_FLAG_FULLSCREEN or //隱藏狀態欄
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or  //隱藏導航欄
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or //粘性沉浸模式
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or //內容顯示在狀態欄後面
                            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION //內容顯示在導航欄後面
            }
        }
    }
}
