package tw.north27.coachingapp.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tw.north27.coachingapp.base.viewModel.BaseAndroidViewModel
import tw.north27.coachingapp.ext.SignInResults
import tw.north27.coachingapp.ext.asLiveData
import tw.north27.coachingapp.model.result.SignInResult
import tw.north27.coachingapp.model.result.SignInState
import tw.north27.coachingapp.module.SignInModule
import tw.north27.coachingapp.repository.inter.ISignInRepository

class SignInViewModel(application: Application, val repo: ISignInRepository) : BaseAndroidViewModel(application) {


    private val _toast = MutableLiveData<Pair<ToastType, String>>()

    val toast = _toast.asLiveData()

    private val signInModule = SignInModule(application)

    private val _signIn = MutableLiveData<SignInResult>()

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
                val result = repo.checkSignInResult(account, password)
                when (result) {
                    is SignInResults.Successful -> {
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

                    is SignInResults.NotAuth -> {
                        //未認證
                    }
                    is SignInResults.ClientErrors -> {
//                    _toast.postValue(ToastType.SIGN_IN to "Code = ${result.code}, Msg = ${result.msg}")
                        _toast.postValue(ToastType.SIGN_IN to "帳密有誤，請重新輸入!")
                    }
                }
            }

        }

    }


}