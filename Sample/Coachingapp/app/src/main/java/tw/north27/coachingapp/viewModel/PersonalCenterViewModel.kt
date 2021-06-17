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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import tw.north27.coachingapp.R
import tw.north27.coachingapp.model.CommentInfo
import tw.north27.coachingapp.model.UserInfo
import tw.north27.coachingapp.repository.IPublicRepository
import tw.north27.coachingapp.repository.IUserRepository

class PersonalCenterViewModel(
    application: Application,
    private val userRepo: IUserRepository,
    private val publicRepo: IPublicRepository
) : BaseAndroidViewModel(application) {

    private val backgroundResList = listOf<Int>(
        R.drawable.ic_personal_center_background1,
        R.drawable.ic_personal_center_background2,
        R.drawable.ic_personal_center_background3,
        R.drawable.ic_personal_center_background4,
        R.drawable.ic_personal_center_background5,
        R.drawable.ic_personal_center_background6,
        R.drawable.ic_personal_center_background7,
        R.drawable.ic_personal_center_background8,
        R.drawable.ic_personal_center_background9
    )

    private val backgroundRes: Int
        get() = backgroundResList[backgroundResList.indices.random()]
    //
    //
    //
    //
    //
    //
    //


    private val _userState: MutableLiveData<ViewState<UserInfo>> by lazy {
        getUser()
        MutableLiveData(ViewState.initial())
    }

    val userState = _userState.asLiveData()

    private fun getUser() = viewModelScope.launch(Dispatchers.IO) {
        _userState.postValue(ViewState.load())
        val account = cxt.userPref.getAccount().first()
        if (account.isEmpty()) {
            _userState.postValue(ViewState.empty())
        } else {
            val results = userRepo.getUser(account)
            when (results) {
                is Results.Successful<UserInfo> -> {
                    val userInfo = results.data
                    _userState.postValue(ViewState.data(userInfo))
                }
                is Results.ClientErrors -> {
                    _userState.postValue(ViewState.error(results.e))
                }
                is Results.NetWorkError -> {
                    _userState.postValue(ViewState.network(results.e))
                }
            }
        }
    }

    private val _commentListState: MutableLiveData<ViewState<List<CommentInfo>>> by lazy {
        viewModelScope.launch(Dispatchers.IO) {
            getCommentList(account = cxt.userPref.getAccount().first())
        }
        MutableLiveData<ViewState<List<CommentInfo>>>(ViewState.initial())
    }

    val commentListState = _commentListState.asLiveData()

    fun getCommentList(account: String, educationId: Long? = 0, gradeId: Long? = 0, subjectId: Long? = 0, unitId: Long? = 0) = viewModelScope.launch(Dispatchers.IO) {
        _commentListState.postValue(ViewState.load())
        val results = publicRepo.getCommentList(account = account, educationId = educationId, gradeId = gradeId, subjectId = subjectId, unitId = unitId)
        when (results) {
            is Results.Successful<List<CommentInfo>> -> {
                if (results.data.isEmpty())
                    _commentListState.postValue(ViewState.empty())
                else
                    _commentListState.postValue(ViewState.data(results.data))
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