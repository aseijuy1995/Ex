package tw.north27.coachingapp.ext

import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.jakewharton.rxrelay3.PublishRelay
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo


fun View.autoBreatheAlphaAnim(owner: LifecycleOwner, disposable: CompositeDisposable) {
    val alphaAnimation = AlphaAnimation(0.1f, 1.0f).apply {
        duration = 1000
        repeatCount = -1
        repeatMode = Animation.REVERSE
    }
    alphaAnimation.autoAnim(owner, disposable)
    animation = alphaAnimation
}

fun Animation.autoAnim(owner: LifecycleOwner, disposable: CompositeDisposable) {
    val autoAnimObs = AutoAnimObs(owner)
    autoAnimObs.animStateRelay.subscribe {
        if (it)
            start()
        else
            cancel()
    }.addTo(disposable)
}

class AutoAnimObs(private val owner: LifecycleOwner) : DefaultLifecycleObserver {

    val animStateRelay = PublishRelay.create<Boolean>()

    init {
        owner.lifecycle.addObserver(this)
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        animStateRelay.accept(true)
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        animStateRelay.accept(false)
    }
}