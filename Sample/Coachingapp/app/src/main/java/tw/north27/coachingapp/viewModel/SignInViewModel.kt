package tw.north27.coachingapp.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yujie.pushmodule.fcm.FirebaseMsg
import com.yujie.core_lib.base.BaseAndroidViewModel
import com.yujie.core_lib.ext.asLiveData
import com.yujie.core_lib.http.Results
import com.yujie.core_lib.pref.clear
import com.yujie.core_lib.pref.setUserPref
import com.yujie.core_lib.pref.userPref
import com.yujie.core_lib.util.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tw.north27.coachingapp.R
import tw.north27.coachingapp.model.response.SignCode
import tw.north27.coachingapp.model.response.SignInRequest
import tw.north27.coachingapp.model.response.SignInfo
import tw.north27.coachingapp.repository.IClientRepository

class SignInViewModel(
    application: Application,
    val clientRepo: IClientRepository
) : BaseAndroidViewModel(application) {

    private val _signInState = MutableLiveData<ViewState<SignInfo>>(ViewState.initial())

    val signInState = _signInState.asLiveData()

    fun signIn(account: String?, password: String?) = viewModelScope.launch(Dispatchers.IO) {
        _signInState.postValue(ViewState.load())
        if (account.isNullOrEmpty() && password.isNullOrEmpty()) {
            _signInState.postValue(ViewState.empty(cxt.getString(R.string.enter_account_password)))
        } else if (account.isNullOrEmpty()) {
            _signInState.postValue(ViewState.empty(cxt.getString(R.string.enter_account)))
        } else if (password.isNullOrEmpty()) {
            _signInState.postValue(ViewState.empty(cxt.getString(R.string.enter_password)))
        } else {
            val results = clientRepo.signIn(
                signInRequest = SignInRequest(
                    account = account,
                    password = password,
                    pushToken = FirebaseMsg.fcmToken!!
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