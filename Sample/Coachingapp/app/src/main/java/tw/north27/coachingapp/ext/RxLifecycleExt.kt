package tw.north27.coachingapp.ext

import androidx.lifecycle.LifecycleOwner
import com.trello.rxlifecycle4.RxLifecycle
import com.trello.rxlifecycle4.android.lifecycle.kotlin.bindToLifecycle
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable


fun <T> Observable<T>.observe() {
    this.compose(RxLifecycle.bind(this)).subscribe()
}


fun <T> Observable<T>.observe(owner: LifecycleOwner): Disposable? {
    return this.bindToLifecycle(owner).subscribe()
}

fun <T> Observable<T>.observe(owner: LifecycleOwner, obs: Observer<T>) {
    this.bindToLifecycle(owner).subscribe(obs)
}