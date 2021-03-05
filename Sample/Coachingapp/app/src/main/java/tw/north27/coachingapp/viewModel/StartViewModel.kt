package tw.north27.coachingapp.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import tw.north27.coachingapp.base.viewModel.BaseAndroidViewModel
import tw.north27.coachingapp.ext.asLiveData
import tw.north27.coachingapp.model.result.AppConfig
import tw.north27.coachingapp.model.result.SignInState
import tw.north27.coachingapp.module.http.ResponseResults
import tw.north27.coachingapp.module.http.SimpleResults
import tw.north27.coachingapp.module.pref.SignInModule
import tw.north27.coachingapp.repository.inter.IPublicRepository
import tw.north27.coachingapp.repository.inter.IUserRepository

class StartViewModel(
    application: Application,
    private val publicRepo: IPublicRepository,
    private val userRepo: IUserRepository
) : BaseAndroidViewModel(application) {

    private val _appConfig = MutableLiveData<AppConfig>()

    val appConfig = _appConfig.asLiveData()

    private val _toast = MutableLiveData<Pair<ToastType, String>>()

    val toast = _toast.asLiveData()

    private val signInModule = SignInModule(application)

    private val _signInState = MutableLiveData<SignInState>()

    val signInState = _signInState.asLiveData()

    enum class ToastType {
        VERSION, CHECK_SIGN_IN
    }

    fun appConfig(fcmToken: String): LiveData<AppConfig> {
        signInModule.setValue(fcmToken = fcmToken)
        viewModelScope.launch {
            val results = publicRepo.getAppConfig(fcmToken)
            when (results) {
                is SimpleResults.Successful -> {
                    _appConfig.postValue(results.data!!)
                }
                is SimpleResults.ClientErrors -> {
                    _toast.postValue(ToastType.VERSION to "${results.error}:無法獲取初始數據")
                }
                is SimpleResults.NetWorkError -> {
                    _toast.postValue(ToastType.VERSION to "${results.error}:網路異常")
                }
            }
        }
        return appConfig
    }

    fun checkSignIn() {
        viewModelScope.launch {
            signInModule.getValue { it.fcmToken }.collectLatest { fcmToken ->
                val results = userRepo.postCheckSignIn(fcmToken)
                when (results) {
                    is ResponseResults.Successful -> {
                        val signInInfo = results.data
                        val account: String
                        val accessToken: String
                        val refreshToken: String
                        val isFirst: Boolean
                        when (signInInfo.signInState) {
                            SignInState.SUCCESS -> {
                                signInInfo.userInfo.also {
                                    account = it.account
                                    accessToken = it.accessToken
                                    refreshToken = it.refreshToken
                                    isFirst = signInInfo.isFirst
                                }
                            }
                            SignInState.FAILURE -> {
                                account = ""
                                accessToken = ""
                                refreshToken = ""
                                isFirst = false
                            }
                        }
                        signInModule.setValue(
                            account = account,
                            accessToken = accessToken,
                            refreshToken = refreshToken,
                            isFirst = isFirst
                        )
                        _signInState.postValue(signInInfo.signInState)
                    }
                    is ResponseResults.ClientErrors -> {
                        _toast.postValue(ToastType.CHECK_SIGN_IN to "${results.code}:登入資料異常")
                    }
                    is ResponseResults.NetWorkError -> {
                        _toast.postValue(ToastType.CHECK_SIGN_IN to "${results.error}:網路異常")
                    }
                }

//            signInModule.getValue { it }.collect {
//                val result = userRepo.postSignIn(account)
//
//
//                when (result) {
//                    is Results.Successful -> {
//                        val sign = result.data!!
//                        //存取User資訊
//                        signInModule.setValue(
//                            guid = sign.guid,
//                            account = sign.account,
//                            accessToken = sign.accessToken,
//                            refreshToken = sign.refreshToken,
//                            expiredTime = sign.expiredTime,
//                            isFirstLogin = sign.isFirstSignIn
//                        )
//                        _signIn.postValue(sign)
//                    }
//                    is Results.ClientErrors -> {
//                        //無權限 - token過期
//                        if (result.code == 403) {
//                            //過期處理
//                        } else {
//                            _toast.postValue(ToastType.CHECK_SIGN_IN to "Code = ${result.code}, Msg = ${result.msg}")
//                        }
//                    }
//                }
//            }
            }

        }
    }


}