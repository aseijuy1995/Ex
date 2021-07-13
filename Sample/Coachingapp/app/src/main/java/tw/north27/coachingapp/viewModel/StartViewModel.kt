package tw.north27.coachingapp.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yujie.utilmodule.base.BaseAndroidViewModel
import com.yujie.utilmodule.ext.asLiveData
import com.yujie.utilmodule.http.Results
import com.yujie.utilmodule.pref.clear
import com.yujie.utilmodule.pref.setUserPref
import com.yujie.utilmodule.pref.userPref
import com.yujie.utilmodule.util.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import tw.north27.coachingapp.model.SignCode
import tw.north27.coachingapp.model.SignInfo
import tw.north27.coachingapp.model.request.SignRequest
import tw.north27.coachingapp.repository.IUserRepository

class StartViewModel(
    application: Application,
    private val userRepo: IUserRepository
) : BaseAndroidViewModel(application) {

    private val _signInState = MutableLiveData<ViewState<SignInfo>>(ViewState.initial())

    val signInState = _signInState.asLiveData()

    fun checkSign() = viewModelScope.launch(Dispatchers.IO) {
        _signInState.postValue(ViewState.load())
        val userPref = cxt.userPref.data.first()
        val id = userPref.id
        if (id.isEmpty() || userPref.accessToken.isEmpty() || userPref.refreshToken.isEmpty()) {
            _signInState.postValue(ViewState.empty())
        } else {
            val results = userRepo.checkSign(
                signRequest = SignRequest(
                    clientId = id
                )
            )
            when (results) {
                is Results.Successful<SignInfo> -> {
                    val signIn = results.data
                    when (signIn.signCode) {
                        SignCode.SIGN_IN_SUC.code -> {
                            val signInInfo = signIn.signInInfo!!
                            val clientInfo = signInInfo.clientInfo!!
                            val tokenInfo = signInInfo.tokenInfo!!
                            cxt.userPref.setUserPref(
                                id = clientInfo.id,
                                account = "",
                                password = "",
                                tokenType = tokenInfo.tokenType,
                                accessToken = tokenInfo.accessToken,
                                refreshToken = tokenInfo.refreshToken,
                                expiresTime = (System.currentTimeMillis() / 1000) + tokenInfo.expiresIn,
                                isFirst = signInInfo.isFirst,
                                pushToken = signInInfo.pushToken,
                                auth = clientInfo.auth,
                            )
                        }
                        SignCode.SIGN_IN_FAIL.code -> {
                            cxt.userPref.clear()
                        }
                    }
                    _signInState.postValue(ViewState.data(signIn))
                }
                is Results.ClientErrors -> {
                    _signInState.postValue(ViewState.error(results.e))
                }
                is Results.NetWorkError -> {
                    _signInState.postValue(ViewState.network(results.e))
                }
            }
        }
    }
}