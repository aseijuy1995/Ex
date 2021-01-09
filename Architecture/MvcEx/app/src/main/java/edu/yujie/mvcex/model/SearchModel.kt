package edu.yujie.mvcex.model

import edu.yujie.mvcex.IApiService
import edu.yujie.mvcex.model.bean.DataBean
import edu.yujie.mvcex.ui.RxBus
import kotlinx.coroutines.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.lang.Thread.sleep
import kotlin.coroutines.CoroutineContext

class SearchModel : KoinComponent, CoroutineScope {
    private val service by inject<IApiService>()
    private var mType: String? = null
    private var mKeyword: String? = null

    fun getType(progress: Int?) {
        mType = when (progress) {
            0 -> "code"
            1 -> "commits"
            2 -> "issues"
            3 -> "labels"
            4 -> "repositories"
            5 -> "topics"
            6 -> "users"
            else -> ""
        }
    }


    fun sendData() {
        val dataEvent = DataEvent(mType, mKeyword)
        RxBus.send(dataEvent)
    }

    fun search(dataEvent: DataEvent, callback: (DataBean) -> Unit?) {
        launch(Dispatchers.IO) {
            when (dataEvent.type) {
                "code" -> {
                    val codeBean = service.searchCode(q = dataEvent.keyword)
                    sleep(1500)
                    refreshCallback(callback, codeBean)
                }
                "commits" -> {
                    val commits = service.searchCommits(q = dataEvent.keyword)
                    sleep(1500)
                    refreshCallback(callback, commits)
                }
                "issues" -> {
                    val issue = service.searchIssue(q = dataEvent.keyword)
                    sleep(1500)
                    refreshCallback(callback, issue)
                }
                "labels" -> {
                    val labels = service.searchLabels(q = dataEvent.keyword)
                    sleep(1500)
                    refreshCallback(callback, labels)
                }
                "repositories" -> {
                    val repositories = service.searchRepositories(q = dataEvent.keyword)
                    sleep(1500)
                    refreshCallback(callback, repositories)
                }
                "topics" -> {
                    val topics = service.searchTopics(q = dataEvent.keyword)
                    sleep(1500)
                    refreshCallback(callback, topics)
                }
                "users" -> {
                    val userBean = service.searchUsers(q = dataEvent.keyword)
                    sleep(1500)
                    refreshCallback(callback, userBean)
                }

            }
        }
    }

    private suspend fun refreshCallback(callback: (DataBean) -> Unit?, dataBean: DataBean) = withContext(Dispatchers.Main) {
        callback(dataBean)
    }

    fun getKeyWord(keyword: String?) {
        mKeyword = if (keyword != null && keyword.isNotEmpty()) keyword else "null"
    }

    override val coroutineContext: CoroutineContext
        get() = Job()


}