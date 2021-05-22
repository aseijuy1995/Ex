package tw.north27.coachingapp.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yujie.basemodule.BaseAndroidViewModel
import com.yujie.prefmodule.dataStore.dataStoreUserPref
import com.yujie.prefmodule.dataStore.getAccount
import com.yujie.prefmodule.dataStore.getUuid
import com.yujie.prefmodule.dataStore.setDelegate
import com.yujie.utilmodule.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import tw.north27.coachingapp.ext2.asLiveData
import tw.north27.coachingapp.model.result.SignIn
import tw.north27.coachingapp.model.result.SignInState
import tw.north27.coachingapp.module.http.Results
import tw.north27.coachingapp.repository.nofinish.IUserRepository

class SignOutViewModel(application: Application, val userRepo: IUserRepository) : BaseAndroidViewModel(application) {

    private val _signOutState = MutableLiveData<ViewState<SignIn>>(ViewState.Initial)

    val signOutState = _signOutState.asLiveData()

    fun signOut() {
        viewModelScope.launch(Dispatchers.IO) {
            _signOutState.postValue(ViewState.load())
            val uuid = cxt.dataStoreUserPref.getUuid().first()
            val account = cxt.dataStoreUserPref.getAccount().first()
            val results = userRepo.signOut(account, uuid)
            when (results) {
                is Results.Successful -> {
                    val signIn = results.data
                    when (signIn.signInState) {
                        SignInState.SIGN_OUT -> {
                            cxt.dataStoreUserPref.setDelegate(
                                account = "",
                                auth = "",
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