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
import kotlinx.coroutines.withContext
import tw.north27.coachingapp.R
import tw.north27.coachingapp.model.Gender
import tw.north27.coachingapp.model.UserInfo
import tw.north27.coachingapp.repository.IPublicRepository
import tw.north27.coachingapp.repository.IUserRepository
import java.util.*

class PersonalEditViewModel(
    application: Application,
    private val userRepo: IUserRepository
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

    private val _updateUserState = MutableLiveData<ViewState<Boolean>>(ViewState.initial())

    val updateUserState = _updateUserState.asLiveData()

    fun updateUser(
        bgPath: String,
        avatarPath: String,
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
        val results = userRepo.updateUser(
            account = account,
            bgPath = bgPath,
            avatarPath = avatarPath,
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
        when (results) {
            is Results.Successful<Boolean> -> {
                _updateUserState.postValue(ViewState.data(results.data))
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


}