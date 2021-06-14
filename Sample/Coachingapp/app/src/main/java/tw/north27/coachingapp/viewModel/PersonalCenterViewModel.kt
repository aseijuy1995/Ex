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
import tw.north27.coachingapp.model.UserInfo
import tw.north27.coachingapp.repository.IUserRepository

class PersonalCenterViewModel(
    application: Application,
    private val userRepo: IUserRepository
) : BaseAndroidViewModel(application) {

    private val _userInfoState = MutableLiveData<ViewState<UserInfo>>(ViewState.initial())

    val userInfoState = _userInfoState.asLiveData()

    fun getUserInfo() = viewModelScope.launch(Dispatchers.IO) {
        _userInfoState.postValue(ViewState.load())
        val account = cxt.userPref.getAccount().first()
        if (account.isEmpty()) {
            _userInfoState.postValue(ViewState.empty())
        } else {
            val results = userRepo.getUserInfo(account)
            when (results) {
                is Results.Successful<UserInfo> -> {
                    val userInfo = results.data
                    _userInfoState.postValue(ViewState.data(userInfo))
                }
                is Results.ClientErrors -> {
                    _userInfoState.postValue(ViewState.error(results.e))
                }
                is Results.NetWorkError -> {
                    _userInfoState.postValue(ViewState.network(results.e))
                }
            }
        }
    }

}