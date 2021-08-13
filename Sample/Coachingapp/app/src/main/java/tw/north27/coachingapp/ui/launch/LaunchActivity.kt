package tw.north27.coachingapp.ui.launch

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowInsets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.flaviofaria.kenburnsview.KenBurnsView
import com.flaviofaria.kenburnsview.Transition
import com.yujie.core_lib.adapter.bindImg
import com.yujie.core_lib.base.BaseAppCompatActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import tw.north27.coachingapp.R
import tw.north27.coachingapp.databinding.ActivityLaunchBinding
import tw.north27.coachingapp.viewModel.PublicViewModel

class LaunchActivity : BaseAppCompatActivity<ActivityLaunchBinding>() {

    private val navFragment: NavHostFragment
        get() = (supportFragmentManager.findFragmentById(R.id.fcv_launch) as NavHostFragment)

    private val navController: NavController
        get() = navFragment.navController

    private val publicVM by viewModel<PublicViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.sharedElementsUseOverlay = false
        binding.ivBg.setTransitionListener(object : KenBurnsView.TransitionListener {
            override fun onTransitionStart(transition: Transition?) {
            }

            override fun onTransitionEnd(transition: Transition?) {
                binding.ivBg.pause()
                binding.ivBg2.apply {
                    bindImg(resId = publicVM.bgRes.value, placeRes = publicVM.bgRes.value!!)
                    resume()
                }
                val animatorSet = AnimatorSet()
                animatorSet
                    .play(ObjectAnimator.ofFloat(binding.ivBg2, "alpha", 0f, 1.0f))
                    .with(ObjectAnimator.ofFloat(binding.ivBg, "alpha", 1.0f, 0f))
                animatorSet.start()

            }
        })


        binding.ivBg2.setTransitionListener(object : KenBurnsView.TransitionListener {
            override fun onTransitionStart(transition: Transition?) {
            }

            override fun onTransitionEnd(transition: Transition?) {
                binding.ivBg2.pause()
                binding.ivBg.apply {
                    bindImg(resId = publicVM.bgRes.value, placeRes = publicVM.bgRes.value!!)
                    resume()
                }
                val animatorSet = AnimatorSet()
                animatorSet
                    .play(ObjectAnimator.ofFloat(binding.ivBg, "alpha", 0f, 1.0f))
                    .with(ObjectAnimator.ofFloat(binding.ivBg2, "alpha", 1.0f, 0f))
                animatorSet.start()
            }
        })


        binding.ivBg.bindImg(
            resId = publicVM.bgRes,
            placeRes = publicVM.bgRes,
        )

        binding.ivBg2.apply {
            bindImg(resId = publicVM.bgRes, placeRes = publicVM.bgRes,)
            alpha = 0f
            pause()
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

    override val inflate: (LayoutInflater) -> ActivityLaunchBinding
        get() = ActivityLaunchBinding::inflate
}
