package tw.north27.coachingapp.base.viewModel

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import timber.log.Timber

open class BaseViewModel : ViewModel() {

    private val TAG = javaClass.simpleName

    protected val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    fun handlerException(e: Exception) = Timber.e(e)

}