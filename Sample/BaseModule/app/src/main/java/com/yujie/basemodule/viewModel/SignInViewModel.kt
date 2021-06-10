package tw.north27.coachingapp.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.yujie.utilmodule.UserPref
import com.yujie.utilmodule.base.BaseAndroidViewModel
import com.yujie.utilmodule.http.ResponseResults
import com.yujie.utilmodule.pref.getDelegate
import com.yujie.utilmodule.pref.setUserPref
import com.yujie.utilmodule.pref.userPref
import com.yujie.utilmodule.util.ViewState
import kotlinx.coroutines.Dispatchers
import tw.north27.coachingapp.R
import tw.north27.coachingapp.model.SignIn
import tw.north27.coachingapp.model.SignInState
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
                val userPref = cxt.userPref.getDelegate { it }.first()
                val uuid = userPref.uuid
                val pushToken = userPref.pushToken
                val results = userRepo.signIn(uuid, account, password, pushToken)
                when (results) {
                    is ResponseResults.Successful -> {
                        val signIn = results.data
                        val accountNew: String
                        val authNew: UserPref.Authority
                        val accessTokenNew: String
                        val refreshTokenNew: String
                        val fcmTokenNew: String
                        val isFirstNew: Boolean
                        when (signIn.signInState) {
                            SignInState.SIGN_IN -> {
                                val signInInfo = signIn.signInInfo!!
                                val userInfo = signInInfo.userInfo!!
                                accountNew = userInfo.account
                                authNew = userInfo.auth
                                accessTokenNew = signInInfo.accessToken!!
                                refreshTokenNew = signInInfo.refreshToken!!
                                fcmTokenNew = signInInfo.fcmToken!!
                                isFirstNew = signInInfo.isFirst!!
                            }
                            SignInState.SIGN_OUT -> {
                                accountNew = ""
                                authNew = UserPref.Authority.UNKNOWN
                                accessTokenNew = ""
                                refreshTokenNew = ""
                                isFirstNew = false
                            }
                        }
                        cxt.userPref.setUserPref(
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