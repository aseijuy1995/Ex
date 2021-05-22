package tw.north27.coachingapp.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yujie.basemodule.BaseAndroidViewModel
import com.yujie.prefmodule.dataStore.dataStoreUserPref
import com.yujie.prefmodule.dataStore.getFcmToken
import com.yujie.prefmodule.dataStore.getUuid
import com.yujie.prefmodule.dataStore.setDelegate
import com.yujie.utilmodule.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import tw.north27.coachingapp.R
import tw.north27.coachingapp.ext2.asLiveData
import tw.north27.coachingapp.model.result.SignIn
import tw.north27.coachingapp.model.result.SignInState
import tw.north27.coachingapp.module.http.ResponseResults
import tw.north27.coachingapp.repository.nofinish.IUserRepository

class SignInViewModel(application: Application, val userRepo: IUserRepository) : BaseAndroidViewModel(application) {

    private val _signInState = MutableLiveData<ViewState<SignIn>>(ViewState.Initial)

    val signInState = _signInState.asLiveData()

    fun signIn(account: String?, password: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            _signInState.postValue(ViewState.load())
            if (account.isNullOrEmpty() && password.isNullOrEmpty()) {
                _signInState.postValue(ViewState.empty(cxt.getString(R.string.enter_account_password)))
            } else if (account.isNullOrEmpty()) {
                _signInState.postValue(ViewState.empty(cxt.getString(R.string.enter_account)))
            } else if (password.isNullOrEmpty()) {
                _signInState.postValue(ViewState.empty(cxt.getString(R.string.enter_password)))
            } else {
                val uuid = cxt.dataStoreUserPref.getUuid().first()
                val fcmToken = cxt.dataStoreUserPref.getFcmToken().first()
                val results = userRepo.signIn(uuid, account, password, fcmToken)
                when (results) {
                    is ResponseResults.Successful -> {
                        val signIn = results.data
                        val accountNew: String
                        val authNew: String
                        val accessTokenNew: String
                        val refreshTokenNew: String
                        val fcmTokenNew: String
                        val isFirstNew: Boolean
                        when (signIn.signInState) {
                            SignInState.SIGN_IN -> {
                                val signInInfo = signIn.signInInfo!!
                                val userInfo = signInInfo.userInfo!!
                                accountNew = userInfo.account
                                authNew = userInfo.auth.toString()
                                accessTokenNew = signInInfo.accessToken!!
                                refreshTokenNew = signInInfo.refreshToken!!
                                fcmTokenNew = signInInfo.fcmToken!!
                                isFirstNew = signInInfo.isFirst!!
                            }
                            SignInState.SIGN_OUT -> {
                                accountNew = ""
                                authNew = ""
                                accessTokenNew = ""
                                refreshTokenNew = ""
                                isFirstNew = false
                            }
                        }
                        cxt.dataStoreUserPref.setDelegate(
                            account = accountNew,
                            auth = authNew,
                            accessToken = accessTokenNew,
                            refreshToken = refreshTokenNew,
                            isFirst = isFirstNew
                        )
                        _signInState.postValue(ViewState.data(signIn))
                    }
                    is ResponseResults.ClientErrors -> {
                        _signInState.postValue(ViewState.error(results.e))
                    }
                    is ResponseResults.NetWorkError -> {
                        _signInState.postValue(ViewState.network(results.e))
                    }
                }
            }
        }
    }


}