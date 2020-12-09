package edu.yujie.rxviewmodel

import androidx.lifecycle.ViewModel
import com.trello.rxlifecycle4.LifecycleProvider
import com.trello.rxlifecycle4.LifecycleTransformer
import com.trello.rxlifecycle4.RxLifecycle
import edu.yujie.rxlifecycleex.ViewModelEvent
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject

open class RxViewModel : ViewModel(), LifecycleProvider<ViewModelEvent> {
    private val subject = BehaviorSubject.create<ViewModelEvent>()
        .also {
            it.onNext(ViewModelEvent.ACTIVE)
        }

    override fun lifecycle(): Observable<ViewModelEvent> = subject.hide()

    override fun <T : Any?> bindUntilEvent(event: ViewModelEvent): LifecycleTransformer<T> =
        RxLifecycle.bindUntilEvent<T, ViewModelEvent>(subject, event)

    override fun <T : Any?> bindToLifecycle(): LifecycleTransformer<T> =
        RxLifecycleViewModel.bindViewModel(subject)
//        RxLifecycle.bind(subject)

    override fun onCleared() {
        super.onCleared()
        subject.onNext(ViewModelEvent.CLEAR)
    }
}