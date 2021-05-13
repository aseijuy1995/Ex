package tw.north27.coachingapp.ui2.public

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tw.north27.coachingapp.base.BaseAndroidViewModel
import tw.north27.coachingapp.ext2.asLiveData
import tw.north27.coachingapp.model.result.SignInfo
import tw.north27.coachingapp.model.result.SignState
import tw.north27.coachingapp.module.http.Results
import tw.north27.coachingapp.module.pref.UserModule
import tw.north27.coachingapp.repository.inter.IUserRepository
import tw.north27.coachingapp.util2.ViewState

class ExitViewModel(application: Application, val userRepo: IUserRepository) : BaseAndroidViewModel(application) {

    private val userModule = UserModule(application)

    private val _signOutState = MutableLiveData<ViewState<SignInfo>>(ViewState.Initial)

    val signOutState = _signOutState.asLiveData()

    fun signOut() {
        _signOutState.postValue(ViewState.load())
        viewModelScope.launch(Dispatchers.IO) {
            val signOut = userModule.getValue { it }.first()
            val results = userRepo.signOut(signOut.account, signOut.deviceId)
            when (results) {
                is Results.Successful -> {
                    val signOutInfo = results.data
                    if (signOutInfo.signState == SignState.SIGN_OUT_SUCCESS) {
                        userModule.setValue(
                            uuid = -1,
                            account = "",
                            accessToken = "",
                            refreshToken = "",
                            isFirst = false
                        )
                        _signOutState.postValue(ViewState.data(signOutInfo))
                    } else {
                        _signOutState.postValue(ViewState.empty())
                    }
                }
                is Results.ClientErrors -> {
                    _signOutState.postValue(ViewState.error(results.e))
                }
                is Results.NetWorkError -> {
                    _signOutState.postValue(ViewState.network(results.e))
                }
            }
        }
    }
}