package tw.north27.coachingapp.ext

import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.jakewharton.rxrelay3.PublishRelay
import io.reactivex.rxjava3.disposables.CompositeDisposable


fun View.autoBreatheAlphaAnim(owner: LifecycleOwner, disposable: CompositeDisposable) {
    val anim = createAlphaAnim()
    this.animation = anim
    //
    val lifeObs = LifeObs(owner)
    lifeObs.foregroundRelay.subscribe {
        if (it) anim.start()
        else anim.cancel()
    }


}

class LifeObs(private val owner: LifecycleOwner) : DefaultLifecycleObserver {

    val foregroundRelay = PublishRelay.create<Boolean>()

    init {
        owner.lifecycle.addObserver(this)
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        foregroundRelay.accept(true)
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        foregroundRelay.accept(false)
    }
}


/**
 * 設置動畫
 * */
fun View.setLifeAnim(anim: Animation): Animation {
    createAlphaAnim()
    this.animation = anim
}

/**
 * 設置透明動畫
 *
 * */
fun createAlphaAnim(
    fromAlpha: Float = 0.1F,
    toAlpha: Float = 1.0F,
    interval: Long = 1000,
    repeatCount: Int = Animation.INFINITE,//重複執行
    repeatMode: Int = Animation.REVERSE//反方向執行
): AlphaAnimation {
    return AlphaAnimation(fromAlpha, toAlpha).apply {
        this.duration = interval
        this.repeatCount = repeatCount
        this.repeatMode = repeatMode
    }
}


//fun Animation.autoAnim(cxt: Context, disposable: CompositeDisposable) {
//    val dataStore = cxt.createDataStorePref()
//    val isAppOnForeground = runBlocking { dataStore.getBoolean(ProcessLifeObs.APP_ON_FOREGROUND).first() }
//
//    autoAnimObs.animStateRelay.subscribe {
//        if (it)
//            start()
//        else
//            cancel()
//    }.addTo(disposable)
//}