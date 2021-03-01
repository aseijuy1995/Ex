package tw.north27.coachingapp.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import tw.north27.coachingapp.base.viewModel.BaseAndroidViewModel
import tw.north27.coachingapp.ext.asLiveData
import tw.north27.coachingapp.module.pref.SignInModule
import tw.north27.coachingapp.model.result.SignInInfo
import tw.north27.coachingapp.repository.inter.IUserRepository

class SignInViewModel(application: Application, val repo: IUserRepository) : BaseAndroidViewModel(application) {


    private val _toast = MutableLiveData<Pair<ToastType, String>>()

    val toast = _toast.asLiveData()

    private val signInModule = SignInModule(application)

    private val _signIn = MutableLiveData<SignInInfo>()

    val signIn = _signIn.asLiveData()

    enum class ToastType {
        SIGN_IN
    }

    fun checkSignIn(account: String?, password: String?) {
//
//        if (account.isNullOrEmpty() && password.isNullOrEmpty()) {
//            _toast.postValue(ToastType.SIGN_IN to "帳密不可為空")
//        } else if (account.isNullOrEmpty()) {
//            _toast.postValue(ToastType.SIGN_IN to "帳號不可為空")
//        } else if (password.isNullOrEmpty()) {
//            _toast.postValue(ToastType.SIGN_IN to "密碼不可為空")
//        } else {
//            viewModelScope.launch {
//                val result = repo.postSignIn(account, password)
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
//                            isFirstLogin = sign.isFirst
//                        )
//                        _signIn.postValue(sign)
//                    }
//
//                    is Results.AuthError -> {
//                        //未認證
//                    }
//                    is Results.ClientErrors -> {
////                    _toast.postValue(ToastType.SIGN_IN to "Code = ${result.code}, Msg = ${result.msg}")
//                        _toast.postValue(ToastType.SIGN_IN to "帳密有誤，請重新輸入!")
//                    }
//                }
//            }
//
//        }

    }


}