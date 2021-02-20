package tw.north27.coachingapp.ext

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import io.reactivex.rxjava3.disposables.CompositeDisposable


fun CompositeDisposable.startDisposablesLifeObs(owner: LifecycleOwner): DisposablesLifeObs = DisposablesLifeObs(owner, this)

class DisposablesLifeObs(
    private val owner: LifecycleOwner,
    private val disposables: CompositeDisposable
) : DefaultLifecycleObserver {

    init {
        owner.lifecycle.addObserver(this)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        disposables.clear()
    }

}