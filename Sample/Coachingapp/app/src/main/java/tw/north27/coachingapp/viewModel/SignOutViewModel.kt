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
import tw.north27.coachingapp.model.request.SignOutRequest
import tw.north27.coachingapp.repository.IUserRepository

class SignOutViewModel(application: Application, val userRepo: IUserRepository) : BaseAndroidViewModel(application) {

    private val _signOutState = MutableLiveData<ViewState<SignIn>>(ViewState.initial())

    val signOutState = _signOutState.asLiveData()

    fun signOut() = viewModelScope.launch(Dispatchers.IO) {
        _signOutState.postValue(ViewState.load())
        val userPref = cxt.userPref.data.first()
        val uuid = userPref.uuid
        val account = userPref.account
        val results = userRepo.signOut(
            SignOutRequest(uuid = uuid, account = account)
        )
        when (results) {
            is Results.Successful -> {
                val signIn = results.data
                when (signIn.signInCode) {
                    SignInCode.SIGN_OUT_SUCCESS.code -> {
                        cxt.userPref.setUserPref(
                            account = "",
                            expireTime = 0L,
                            accessToken = "",
                            refreshToken = "",
                            isFirst = false,
                            auth = UserPref.Authority.NONE,
                        )
                    }
                    SignInCode.SIGN_OUT_FAILED.code -> {

                    }
                }
                _signOutState.postValue(ViewState.data(signIn))
            }
            is Results.ClientErrors -> {
                _signOutState.postValue(ViewState.error(results.e))
            }
            is Results.NetWorkError -> {
                _signOutState.postValue(ViewState.network(results.e))
            }
        }
    }

}