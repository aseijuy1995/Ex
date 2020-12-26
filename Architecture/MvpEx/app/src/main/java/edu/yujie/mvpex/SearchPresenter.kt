package edu.yujie.mvpex

import android.text.TextUtils
import kotlinx.coroutines.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.coroutines.CoroutineContext

//@OptIn(KoinApiExtension::class)
class SearchPresenter(private val view: IView) : IPresenter, KoinComponent, CoroutineScope {
    private val service by inject<IApiService>()

    override fun checkEmpty(text: String?): Boolean {
        view.onSearching()
        val isEmpty = TextUtils.isEmpty(text)
        if (isEmpty) {
            view.onSearchEmpty()
        }
        return isEmpty
    }

    override fun search(text: String) {
        launch(Dispatchers.IO) {
            val repo = service.searchRepo(text)
            withContext(Dispatchers.Main) {
                view.onSearchComplete(repo)
            }
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Job()
}