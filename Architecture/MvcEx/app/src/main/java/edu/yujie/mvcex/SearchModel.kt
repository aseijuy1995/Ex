package edu.yujie.mvcex

import android.text.TextUtils
import edu.yujie.mvcex.bean.RepoBean
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class SearchModel(private val service: IApiService) : CoroutineScope {

    fun search(text: String, callback: (RepoBean) -> Unit) = launch(Dispatchers.IO) {
        val repo = service.searchRepo(text)
        withContext(Dispatchers.Main) {
            callback(repo)
        }
    }

    fun check(text: String?): Boolean = TextUtils.isEmpty(text)

    override val coroutineContext: CoroutineContext
        get() = Job()
}