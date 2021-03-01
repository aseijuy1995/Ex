package tw.north27.coachingapp.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tw.north27.coachingapp.base.viewModel.BaseAndroidViewModel
import tw.north27.coachingapp.ext.asLiveData
import tw.north27.coachingapp.model.result.AppConfig
import tw.north27.coachingapp.model.result.SignInInfo
import tw.north27.coachingapp.model.result.SignInState
import tw.north27.coachingapp.module.http.Results
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

    fun appConfig(): LiveData<AppConfig> {
        viewModelScope.launch {
            val results = publicRepo.getAppConfig()
            when (results) {
                is SimpleResults.Successful -> {
                    _appConfig.postValue(results.data!!)
                }
                is SimpleResults.ClientErrors -> {
                    _toast.postValue(ToastType.VERSION to "message: ${results.error.message}")
                }
                is SimpleResults.NetWorkError -> {
                    _toast.postValue(ToastType.VERSION to "message: ${results.error.message}")
                }
            }
        }
        return appConfig
    }

    fun checkSignIn() {
        viewModelScope.launch {
            val results = userRepo.postCheckSignIn()
            when (results) {
                is Results.Successful -> {
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
                            signInInfo.userInfo.also {
                                account = ""
                                accessToken = ""
                                refreshToken = ""
                                isFirst = false
                            }
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
                is Results.ClientErrors -> {
                    _toast.postValue(ToastType.CHECK_SIGN_IN to "Code = ${results.code}")
                }
                is Results.NetWorkError -> {
                    _toast.postValue(ToastType.CHECK_SIGN_IN to "Msg = ${results.error.message}")
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