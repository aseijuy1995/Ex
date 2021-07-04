package tw.north27.coachingapp.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yujie.utilmodule.UserPref
import com.yujie.utilmodule.base.BaseAndroidViewModel
import com.yujie.utilmodule.ext.asLiveData
import com.yujie.utilmodule.http.Results
import com.yujie.utilmodule.pref.setUserPref
import com.yujie.utilmodule.pref.userPref
import com.yujie.utilmodule.util.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import tw.north27.coachingapp.model.SignIn
import tw.north27.coachingapp.model.SignInCode
import tw.north27.coachingapp.model.SignInInfo
import tw.north27.coachingapp.repository.IUserRepository

class StartViewModel(
    application: Application,
    private val userRepo: IUserRepository
) : BaseAndroidViewModel(application) {

    private val _signInState = MutableLiveData<ViewState<SignIn>>(ViewState.initial())

    val signInState = _signInState.asLiveData()

    fun checkSignIn() = viewModelScope.launch(Dispatchers.IO) {
        _signInState.postValue(ViewState.load())
        val userPref = cxt.userPref.data.first()
        val account = userPref.account
        val accessToken = userPref.accessToken
        if (account.isEmpty() || accessToken.isEmpty()) {
            _signInState.postValue(ViewState.empty())
        } else {
            val results = userRepo.checkSignIn(account = account)
            when (results) {
                is Results.Successful<SignIn> -> {
                    val signIn = results.data
                    val uuidNew: String
                    val accountNew: String
                    val expireTimeNew: Long
                    val accessTokenNew: String
                    val refreshTokenNew: String
                    val isFirstNew: Boolean
                    val pushTokenNew: String
                    val authNew: UserPref.Authority
                    when (signIn.signInCode) {
                        SignInCode.SIGN_IN_SUC.code -> {
                            val signInInfo = signIn.signInInfo!!
                            val userInfo = signInInfo.userInfo!!
                            accountNew = userInfo.account
                            expireTimeNew = signInInfo.expireTime
                            accessTokenNew = signInInfo.accessToken
                            refreshTokenNew = signInInfo.refreshToken
                            isFirstNew = signInInfo.isFirst
                            pushTokenNew = signInInfo.pushToken
                            authNew = userInfo.auth
                            cxt.userPref.setUserPref(
                                account = accountNew,
                                expireTime = expireTimeNew,
                                accessToken = accessTokenNew,
                                refreshToken = refreshTokenNew,
                                isFirst = isFirstNew,
                                pushToken = pushTokenNew,
                                auth = authNew,
                            )
                        }
                        SignInCode.SIGN_IN_FAIL.code -> {
                            accountNew = ""
                            expireTimeNew = 0L
                            accessTokenNew = ""
                            refreshTokenNew = ""
                            isFirstNew = false
                            pushTokenNew = ""
                            authNew = UserPref.Authority.UNKNOWN
                            cxt.userPref.setUserPref(
                                account = accountNew,
                                expireTime = expireTimeNew,
                                accessToken = accessTokenNew,
                                refreshToken = refreshTokenNew,
                                isFirst = isFirstNew,
                                pushToken = pushTokenNew,
                                auth = authNew,
                            )
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