package tw.north27.coachingapp.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import tw.north27.coachingapp.base.viewModel.BaseAndroidViewModel
import tw.north27.coachingapp.ext.asLiveData
import tw.north27.coachingapp.model.result.SignInInfo
import tw.north27.coachingapp.model.result.SignInState
import tw.north27.coachingapp.module.http.ResponseResults
import tw.north27.coachingapp.module.pref.SignInModule
import tw.north27.coachingapp.repository.inter.IUserRepository

class SignInViewModel(application: Application, val userRepo: IUserRepository) : BaseAndroidViewModel(application) {


    private val _toast = MutableLiveData<Pair<ToastType, String>>()

    val toast = _toast.asLiveData()

    private val signInModule = SignInModule(application)

    private val _signIn = MutableLiveData<SignInInfo>()

    val signIn = _signIn.asLiveData()

    enum class ToastType {
        SIGN_IN
    }

    fun checkSignIn(account: String?, password: String?) {
        if (account.isNullOrEmpty() && password.isNullOrEmpty()) {
            _toast.postValue(ToastType.SIGN_IN to "帳密不可為空")
        } else if (account.isNullOrEmpty()) {
            _toast.postValue(ToastType.SIGN_IN to "帳號不可為空")
        } else if (password.isNullOrEmpty()) {
            _toast.postValue(ToastType.SIGN_IN to "密碼不可為空")
        } else {
            viewModelScope.launch {
                signInModule.getValue { it.fcmToken }.collectLatest { fcmToken ->
                    val results = userRepo.postSignIn(account, password, "device001", fcmToken)
                    when (results) {
                        is ResponseResults.Successful -> {
                            val signInInfo = results.data
                            val uuid: Long
                            val account: String
                            val accessToken: String
                            val refreshToken: String
                            val isFirst: Boolean
                            when (signInInfo.signInState) {
                                SignInState.SUCCESS -> {
                                    signInInfo.userInfo.also {
                                        uuid = 0
                                        account = it.account
                                        accessToken = it.accessToken
                                        refreshToken = it.refreshToken
                                        isFirst = signInInfo.isFirst
                                    }
                                }
                                SignInState.FAILURE -> {
                                    uuid = 1
                                    account = ""
                                    accessToken = ""
                                    refreshToken = ""
                                    isFirst = false
                                }
                            }
                            signInModule.setValue(
                                uuid = 0,
                                account = account,
                                accessToken = accessToken,
                                refreshToken = refreshToken,
                                deviceId = "deviceId001",
                                isFirst = isFirst
                            )
                            _signIn.postValue(signInInfo)
                        }

                        is ResponseResults.ClientErrors -> {
                            _toast.postValue(ToastType.SIGN_IN to "${results.code}:帳密有誤，請重新輸入!")
                        }

                        is ResponseResults.NetWorkError -> {
                            _toast.postValue(ToastType.SIGN_IN to "${results.error}:網路異常")
                        }
                    }
                }
            }

        }

    }


}