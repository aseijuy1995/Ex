package tw.north27.coachingapp.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.yujie.utilmodule.base.BaseAndroidViewModel
import com.yujie.utilmodule.ext.asLiveData
import com.yujie.utilmodule.http.Results
import com.yujie.utilmodule.pref.getAccount
import com.yujie.utilmodule.pref.userPref
import com.yujie.utilmodule.util.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import tw.north27.coachingapp.R
import tw.north27.coachingapp.model.CommentInfo
import tw.north27.coachingapp.model.Gender
import tw.north27.coachingapp.model.UserInfo
import tw.north27.coachingapp.repository.IPublicRepository
import tw.north27.coachingapp.repository.IUserRepository

class PersonalViewModel(
    application: Application,
    private val userRepo: IUserRepository,
    private val publicRepo: IPublicRepository
) : BaseAndroidViewModel(application) {

    private val _bgRes: MutableLiveData<Int> by lazy {
        val list = listOf<Int>(
            R.drawable.ic_personal_bg1,
            R.drawable.ic_personal_bg2,
            R.drawable.ic_personal_bg3,
            R.drawable.ic_personal_bg4,
            R.drawable.ic_personal_bg5,
            R.drawable.ic_personal_bg6,
            R.drawable.ic_personal_bg7,
            R.drawable.ic_personal_bg8,
            R.drawable.ic_personal_bg9
        )
        MutableLiveData(list[list.indices.random()])
    }
    val bgRes = _bgRes.asLiveData()

    private val _userState = MutableLiveData<ViewState<UserInfo>>(ViewState.initial())

    val userState = _userState.asLiveData()

    suspend fun getUser(): ViewState<UserInfo> = withContext(Dispatchers.IO) {
        _userState.postValue(ViewState.load())
        val account = cxt.userPref.getAccount().first()
        if (account.isEmpty()) {
            ViewState.empty()
        } else {
            val results = userRepo.getUser(account)
            when (results) {
                is Results.Successful<UserInfo> -> {
                    val userInfo = results.data
                    _userState.postValue(ViewState.data(userInfo))
                    ViewState.data(userInfo)
                }
                is Results.ClientErrors -> {
                    _userState.postValue(ViewState.error(results.e))
                    ViewState.error(results.e)
                }
                is Results.NetWorkError -> {
                    _userState.postValue(ViewState.network(results.e))
                    ViewState.network(results.e)
                }
            }
        }
    }

    private val _commentListState = MutableLiveData<ViewState<List<CommentInfo>>>(ViewState.initial())

    val commentListState = _commentListState.asLiveData()

    suspend fun getCommentList(educationId: Long? = null, gradeId: Long? = null, subjectId: Long? = null, unitId: Long? = null, index: Int, num: Int) = withContext(Dispatchers.IO) {
        _commentListState.postValue(ViewState.load())
        val account = cxt.userPref.getAccount().first()
        val results = publicRepo.getCommentList(account = account, educationId = educationId, gradeId = gradeId, subjectId = subjectId, unitId = unitId, index = index, num = num)
        when (results) {
            is Results.Successful<List<CommentInfo>> -> {
                if (results.data.isEmpty()) {
                    _commentListState.postValue(ViewState.empty())
                    ViewState.empty()
                } else {
                    _commentListState.postValue(ViewState.data(results.data))
                    ViewState.data(results.data)
                }
            }
            is Results.ClientErrors -> {
                _commentListState.postValue(ViewState.error(results.e))
                ViewState.error(results.e)
            }
            is Results.NetWorkError -> {
                _commentListState.postValue(ViewState.network(results.e))
                ViewState.network(results.e)
            }
        }
    }

    private val _genderList = MutableLiveData<List<Pair<Gender, String>>>(
        listOf(
            Gender.MALE to cxt.getString(R.string.male),
            Gender.FEMALE to cxt.getString(R.string.female)
        )
    )

    val genderList = _genderList.asLiveData()


}