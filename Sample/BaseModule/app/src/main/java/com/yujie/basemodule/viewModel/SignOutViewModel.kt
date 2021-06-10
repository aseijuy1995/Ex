package tw.north27.coachingapp.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yujie.utilmodule.UserPref
import com.yujie.utilmodule.base.BaseAndroidViewModel
import com.yujie.utilmodule.http.Results
import com.yujie.utilmodule.pref.getDelegate
import com.yujie.utilmodule.pref.setUserPref
import com.yujie.utilmodule.pref.userPref
import com.yujie.utilmodule.util.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import tw.north27.coachingapp.ext2.asLiveData
import tw.north27.coachingapp.model.SignIn
import tw.north27.coachingapp.model.SignInState
import tw.north27.coachingapp.repository.nofinish.IUserRepository

class SignOutViewModel(application: Application, val userRepo: IUserRepository) : BaseAndroidViewModel(application) {

    private val _signOutState = MutableLiveData<ViewState<SignIn>>(ViewState.Initial)

    val signOutState = _signOutState.asLiveData()

    fun signOut() {
        viewModelScope.launch(Dispatchers.IO) {
            _signOutState.postValue(ViewState.load())
            val userPref = cxt.userPref.getDelegate { it }.first()
            val uuid = userPref.uuid
            val account = userPref.account
            val results = userRepo.signOut(account, uuid)
            when (results) {
                is Results.Successful -> {
                    val signIn = results.data
                    when (signIn.signInState) {
                        SignInState.SIGN_OUT -> {
                            cxt.userPref.setUserPref(
                                account = "",
                                auth = UserPref.Authority.UNKNOWN,
                                accessToken = "",
                                refreshToken = "",
                                isFirst = false
                            )
                        }
                        SignInState.SIGN_IN -> {

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
}