package tw.north27.coachingapp.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yujie.core_lib.base.BaseAndroidViewModel
import com.yujie.core_lib.ext.asLiveData
import com.yujie.core_lib.http.Results
import com.yujie.core_lib.pref.getAccount
import com.yujie.core_lib.pref.userPref
import com.yujie.core_lib.util.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import tw.north27.coachingapp.model.CommentInfo
import tw.north27.coachingapp.model.Gender
import tw.north27.coachingapp.model.request.CommentRequest
import tw.north27.coachingapp.model.request.ReflectRequest
import tw.north27.coachingapp.model.request.UpdateClientRequest
import tw.north27.coachingapp.model.response.ReflectResponse
import tw.north27.coachingapp.model.response.UpdateClientResponse
import tw.north27.coachingapp.repository.IAskRoomRepository
import tw.north27.coachingapp.repository.IUserRepository
import java.util.*

class PersonalViewModel(
    application: Application,
    private val userRepo: IUserRepository,
    private val askRoomRepo: IAskRoomRepository,
) : BaseAndroidViewModel(application) {

    private val _commentListState = MutableLiveData<ViewState<List<CommentInfo>>>(ViewState.initial())

    val commentListState = _commentListState.asLiveData()

    fun fetchCommentList(
        score: Double? = null,
        educationLevelId: Long? = null,
        gradeId: Long? = null,
        subjectId: Long? = null,
        unitId: Long? = null,
        index: Int,
        num: Int
    ) = viewModelScope.launch(Dispatchers.IO) {
        _commentListState.postValue(ViewState.load())
        val account = cxt.userPref.getAccount().first()
        val results = askRoomRepo.fetchCommentList(
            CommentRequest(clientId = account, score = score, educationLevelId = educationLevelId, gradeId = gradeId, subjectId = subjectId, unitId = unitId, index = index, num = num)
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

    //
    private val _updateUserState = MutableLiveData<ViewState<Boolean>>(ViewState.initial())

    val updateUserState = _updateUserState.asLiveData()

    fun updateUser(
        bgUrl: String,
        avatarUrl: String,
        name: String,
        gender: Gender,
        intro: String,
        birthday: Date? = null,
        cellPhone: String,
        homePhone: String,
        email: String,
        school: String? = null,
        gradeId: Long? = null,
    ) = viewModelScope.launch(Dispatchers.IO) {
        _updateUserState.postValue(ViewState.load())
        val account = cxt.userPref.getAccount().first()
        val results = userRepo.updateClient(
            UpdateClientRequest(
                clientId = account,
                bgUrl = bgUrl,
                avatarUrl = avatarUrl,
                name = name,
                gender = gender,
                intro = intro,
                birthday = birthday,
                cellPhone = cellPhone,
                homePhone = homePhone,
                email = email,
                school = school,
                gradeId = gradeId
            )
        )
        when (results) {
            is Results.Successful<UpdateClientResponse> -> {
                val updateClientResponse = results.data
                _updateUserState.postValue(ViewState.data(updateClientResponse.isSuccess))
                ViewState.data(results.data)
            }
            is Results.ClientErrors -> {
                _updateUserState.postValue(ViewState.error(results.e))
                ViewState.error(results.e)
            }
            is Results.NetWorkError -> {
                _updateUserState.postValue(ViewState.network(results.e))
                ViewState.network(results.e)
            }
        }
    }

    private val _isReflectState = MutableLiveData<ViewState<ReflectResponse>>()

    val isReflectState = _isReflectState.asLiveData()

    fun insertReflect(id: Long, content: String) = viewModelScope.launch(Dispatchers.IO) {
        _isReflectState.postValue(ViewState.load())
        val account = cxt.userPref.getAccount().first()
        val results = askRoomRepo.insertReflect(
            reflectRequest = ReflectRequest(
                account = account,
                id = id,
                content = content
            )
        )
        when (results) {
            is Results.Successful<ReflectResponse> -> {
                _isReflectState.postValue(ViewState.data(results.data))
            }
            is Results.ClientErrors -> {
                _isReflectState.postValue(ViewState.error(results.e))
            }
            is Results.NetWorkError -> {
                _isReflectState.postValue(ViewState.network(results.e))
            }
        }
    }


}