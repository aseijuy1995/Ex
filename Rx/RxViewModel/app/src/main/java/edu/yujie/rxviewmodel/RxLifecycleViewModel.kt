package edu.yujie.rxviewmodel

import com.trello.rxlifecycle4.LifecycleTransformer
import com.trello.rxlifecycle4.OutsideLifecycleException
import com.trello.rxlifecycle4.RxLifecycle.bind
import edu.yujie.rxlifecycleex.ViewModelEvent
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.Function

class RxLifecycleViewModel private constructor() {
    private val TAG = javaClass.simpleName

    init {
        throw AssertionError("$TAG init() No instances")
    }

    companion object {
        fun <T> bindViewModel(lifecycle: Observable<ViewModelEvent>): LifecycleTransformer<T> =
            bind(lifecycle, VIEW_MODEL_LIFECYCLE)

        private val VIEW_MODEL_LIFECYCLE = Function<ViewModelEvent, ViewModelEvent> {
            when (it) {
                ViewModelEvent.ACTIVE -> ViewModelEvent.ACTIVE
                ViewModelEvent.CLEAR -> throw OutsideLifecycleException("RxLifecycleViewModel VIEW_MODEL_LIFECYCLE = ViewModelEvent.CLEAR, Cannot bind to view model lifecycle when outside of it.")
            }
        }
    }
}