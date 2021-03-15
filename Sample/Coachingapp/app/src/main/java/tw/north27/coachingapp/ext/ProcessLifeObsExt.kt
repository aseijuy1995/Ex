package tw.north27.coachingapp.ext

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.jakewharton.rxrelay3.BehaviorRelay

object ProcessLifeObs : DefaultLifecycleObserver {

    val appStateRelay = BehaviorRelay.create<AppState>()

    init {
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        appStateRelay.accept(AppState.FOREGROUND)
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        appStateRelay.accept(AppState.BACKGROUND)
    }
}

fun getAppState(): BehaviorRelay<AppState> = ProcessLifeObs.appStateRelay

enum class AppState {
    FOREGROUND,//前景
    BACKGROUND//背景
}