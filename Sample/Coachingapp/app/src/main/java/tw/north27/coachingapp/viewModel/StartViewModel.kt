package tw.north27.coachingapp.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import tw.north27.coachingapp.base.viewModel.BaseAndroidViewModel
import tw.north27.coachingapp.ext.Results
import tw.north27.coachingapp.ext.asLiveData
import tw.north27.coachingapp.model.result.SignInResult
import tw.north27.coachingapp.model.result.VersionResult
import tw.north27.coachingapp.module.SignInModule
import tw.north27.coachingapp.repository.inter.IStartRepository

class StartViewModel(application: Application, private val repo: IStartRepository) : BaseAndroidViewModel(application) {

    private val _version = MutableLiveData<VersionResult>()

    private val _toast = MutableLiveData<Pair<ToastType, String>>()

    val toast = _toast.asLiveData()

    private val _signIn = MutableLiveData<SignInResult>()

    val signIn = _signIn.asLiveData()

    private val signInModule = SignInModule(application)

    enum class ToastType {
        VERSION, CHECK_SIGN_IN
    }

    fun getVersion(): LiveData<VersionResult> {
        viewModelScope.launch {
            val result = repo.getVersionCtrlResult()
            when (result) {
                is Results.Successful -> {
                    _version.postValue(result.data!!)
                }
                is Results.ClientErrors -> {
                    _toast.postValue(ToastType.VERSION to "Code = ${result.code}, Msg = ${result.msg}")
                }
            }
        }
        return _version.asLiveData()
    }

    fun checkSignIn() {
        viewModelScope.launch {
            signInModule.getValue { it }.collect {
                val result = repo.checkSignInResult(account = it.account)
                when (result) {
                    is Results.Successful -> {
                        val sign = result.data!!
                        //存取User資訊
                        signInModule.setValue(
                            guid = sign.guid,
                            account = sign.account,
                            accessToken = sign.accessToken,
                            refreshToken = sign.refreshToken,
                            expiredTime = sign.expiredTime,
                            isFirstLogin = sign.isFirstSignIn
                        )
                        _signIn.postValue(sign)
                    }
                    is Results.ClientErrors -> {
                        //無權限 - token過期
                        if (result.code == 403) {
                            //過期處理
                        } else {
                            _toast.postValue(ToastType.CHECK_SIGN_IN to "Code = ${result.code}, Msg = ${result.msg}")
                        }
                    }
                }
            }

        }
    }

}