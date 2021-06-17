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
import tw.north27.coachingapp.model.*
import tw.north27.coachingapp.model.Unit
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
            getCommentList()
        }
        MutableLiveData<ViewState<List<CommentInfo>>>(ViewState.initial())
    }

    val commentListState = _commentListState.asLiveData()

    fun getCommentList(educationId: Long? = 0, gradeId: Long? = 0, subjectId: Long? = 0, unitId: Long? = 0) = viewModelScope.launch(Dispatchers.IO) {
        _commentListState.postValue(ViewState.load())
        val account = cxt.userPref.getAccount().first()
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

    val defaultEducation = Education(id = null, text = "預設")

    val defaultGradle = Grade(id = null, text = "預設", educationId = null)

    val defaultSubject = Subject(id = null, text = "預設", educationIdList = listOf(), gradleIdList = listOf())

    val defaultUnit = Unit(id = null, text = "預設", educationId = null, gradeId = null, subjectId = null)

    private val _educationListState: MutableLiveData<ViewState<List<Education>>> by lazy {
        getEducationList()
        MutableLiveData<ViewState<List<Education>>>(ViewState.initial())
    }

    val educationListState = _educationListState.asLiveData()

    fun getEducationList() = viewModelScope.launch(Dispatchers.IO) {
        _gradeListState.postValue(ViewState.load())
        val results = publicRepo.getEducationList()
        when (results) {
            is Results.Successful<List<Education>> -> {
                if (results.data.isEmpty())
                    _educationListState.postValue(ViewState.empty())
                else
                    _educationListState.postValue(ViewState.data(results.data))
            }
            is Results.ClientErrors -> {
                _educationListState.postValue(ViewState.error(results.e))
            }
            is Results.NetWorkError -> {
                _educationListState.postValue(ViewState.network(results.e))
            }
        }
    }

    private val _gradeListState: MutableLiveData<ViewState<List<Grade>>> by lazy {
        getGradeList()
        MutableLiveData<ViewState<List<Grade>>>(ViewState.initial())
    }

    val gradeListState = _gradeListState.asLiveData()

    fun getGradeList(educationId: Long? = 0) = viewModelScope.launch(Dispatchers.IO) {
        _gradeListState.postValue(ViewState.load())
        val results = publicRepo.getGradeList(educationId = educationId)
        when (results) {
            is Results.Successful<List<Grade>> -> {
                if (results.data.isEmpty())
                    _gradeListState.postValue(ViewState.empty())
                else
                    _gradeListState.postValue(ViewState.data(results.data))
            }
            is Results.ClientErrors -> {
                _gradeListState.postValue(ViewState.error(results.e))
            }
            is Results.NetWorkError -> {
                _gradeListState.postValue(ViewState.network(results.e))
            }
        }
    }

    private val _subjectListState: MutableLiveData<ViewState<List<Subject>>> by lazy {
        getSubjectList()
        MutableLiveData<ViewState<List<Subject>>>(ViewState.initial())
    }

    val subjectListState = _subjectListState.asLiveData()

    fun getSubjectList(educationId: Long? = 0, gradeId: Long? = 0) = viewModelScope.launch(Dispatchers.IO) {
        _subjectListState.postValue(ViewState.load())
        val results = publicRepo.getSubjectList(educationId = educationId, gradeId = gradeId)
        when (results) {
            is Results.Successful<List<Subject>> -> {
                if (results.data.isEmpty())
                    _subjectListState.postValue(ViewState.empty())
                else
                    _subjectListState.postValue(ViewState.data(results.data))
            }
            is Results.ClientErrors -> {
                _subjectListState.postValue(ViewState.error(results.e))
            }
            is Results.NetWorkError -> {
                _subjectListState.postValue(ViewState.network(results.e))
            }
        }
    }

    private val _unitListState: MutableLiveData<ViewState<List<Unit>>> by lazy {
        getUnitList()
        MutableLiveData<ViewState<List<Unit>>>(ViewState.initial())
    }

    val unitListState = _unitListState.asLiveData()

    fun getUnitList(educationId: Long? = 0, gradeId: Long? = 0, subjectId: Long? = 0) = viewModelScope.launch(Dispatchers.IO) {
        _unitListState.postValue(ViewState.load())
        val results = publicRepo.getUnitList(educationId = educationId, gradeId = gradeId, subjectId = subjectId)
        when (results) {
            is Results.Successful<List<Unit>> -> {
                if (results.data.isEmpty())
                    _unitListState.postValue(ViewState.empty())
                else
                    _unitListState.postValue(ViewState.data(results.data))
            }
            is Results.ClientErrors -> {
                _unitListState.postValue(ViewState.error(results.e))
            }
            is Results.NetWorkError -> {
                _unitListState.postValue(ViewState.network(results.e))
            }
        }
    }

}