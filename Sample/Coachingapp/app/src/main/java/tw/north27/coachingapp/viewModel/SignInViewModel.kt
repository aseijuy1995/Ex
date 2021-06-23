package tw.north27.coachingapp.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.yujie.pushmodule.fcm.FirebaseMsg
import com.yujie.utilmodule.UserPref
import com.yujie.utilmodule.base.BaseAndroidViewModel
import com.yujie.utilmodule.ext.asLiveData
import com.yujie.utilmodule.http.Results
import com.yujie.utilmodule.pref.getUuid
import com.yujie.utilmodule.pref.setUserPref
import com.yujie.utilmodule.pref.setUuid
import com.yujie.utilmodule.pref.userPref
import com.yujie.utilmodule.util.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import tw.north27.coachingapp.R
import tw.north27.coachingapp.model.SignIn
import tw.north27.coachingapp.model.SignInBody
import tw.north27.coachingapp.model.SignInCode
import tw.north27.coachingapp.repository.IUserRepository
import java.util.*

class SignInViewModel(
    application: Application,
    val userRepo: IUserRepository
) : BaseAndroidViewModel(application) {

    private val _signInState = MutableLiveData<ViewState<SignIn>>(ViewState.initial())

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
            var uuid = cxt.userPref.getUuid().first()
            if (uuid.isEmpty()) {
                uuid = UUID.randomUUID().toString()
                cxt.userPref.setUuid(uuid = uuid)
            }
            val results = userRepo.signIn(
                json = Gson().toJson(
                    SignInBody(
                        uuid = uuid,
                        account = account,
                        password = password,
                        pushToken = FirebaseMsg.fcmToken!!
                    )
                )
            )
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
                            uuidNew = userInfo.id
                            accountNew = userInfo.account
                            expireTimeNew = signInInfo.expireTime!!
                            accessTokenNew = signInInfo.accessToken!!
                            refreshTokenNew = signInInfo.refreshToken!!
                            isFirstNew = signInInfo.isFirst!!
                            pushTokenNew = signInInfo.pushToken!!
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