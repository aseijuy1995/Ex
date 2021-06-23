package tw.north27.coachingapp.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yujie.utilmodule.base.BaseAndroidViewModel
import com.yujie.utilmodule.ext.asLiveData
import com.yujie.utilmodule.http.Results
import com.yujie.utilmodule.pref.getAccount
import com.yujie.utilmodule.pref.userPref
import com.yujie.utilmodule.util.ViewState
import com.yujie.utilmodule.util.logD
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import tw.north27.coachingapp.model.CommentBody
import tw.north27.coachingapp.model.CommentInfo
import tw.north27.coachingapp.repository.IUserRepository

class PersonalViewModel(
    application: Application,
    private val userRepo: IUserRepository
) : BaseAndroidViewModel(application) {

    private val _commentListState = MutableLiveData<ViewState<List<CommentInfo>>>(ViewState.initial())

    val commentListState = _commentListState.asLiveData()

    fun getCommentList(
        score: Double? = null,
        educationId: Long? = null,
        gradeId: Long? = null,
        subjectId: Long? = null,
        unitId: Long? = null,
        index: Int,
        num: Int
    ) = viewModelScope.launch(Dispatchers.IO) {
        _commentListState.postValue(ViewState.load())
        val account = cxt.userPref.getAccount().first()
        val results = userRepo.getCommentList(
            CommentBody(account = account, score = score, educationId = educationId, gradeId = gradeId, subjectId = subjectId, unitId = unitId, index = index, num = num)
        )
        when (results) {
            is Results.Successful<List<CommentInfo>> -> {
                if (results.data.isEmpty()) {
                    _commentListState.postValue(ViewState.empty())
                } else {
                    _commentListState.postValue(ViewState.data(results.data))
                }
            }
            is Results.ClientErrors -> {
                _commentListState.postValue(ViewState.error(results.e))
            }
            is Results.NetWorkError -> {
                _commentListState.postValue(ViewState.network(results.e))
            }
        }
    }

}