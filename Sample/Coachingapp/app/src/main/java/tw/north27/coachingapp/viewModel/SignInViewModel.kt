package tw.north27.coachingapp.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yujie.prefmodule.dataStore.dataStoreUserPref
import com.yujie.prefmodule.dataStore.getDeviceId
import com.yujie.prefmodule.dataStore.getFcmToken
import com.yujie.prefmodule.dataStore.setDelegate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import tw.north27.coachingapp.R
import tw.north27.coachingapp.base.BaseAndroidViewModel
import tw.north27.coachingapp.ext2.asLiveData
import tw.north27.coachingapp.model.result.SignInfo
import tw.north27.coachingapp.model.result.SignState
import tw.north27.coachingapp.module.http.ResponseResults
import tw.north27.coachingapp.repository.inter.IUserRepository
import tw.north27.coachingapp.util2.ViewState

class SignInViewModel(application: Application, val userRepo: IUserRepository) : BaseAndroidViewModel(application) {

    private val _signInState = MutableLiveData<ViewState<SignInfo>>(ViewState.Initial)

    val signInState = _signInState.asLiveData()

    fun signIn(account: String?, password: String?) {
        _signInState.postValue(ViewState.load())
        if (account.isNullOrEmpty() && password.isNullOrEmpty()) {
            _signInState.postValue(ViewState.empty(context.getString(R.string.empty_account_password)))
        } else if (account.isNullOrEmpty()) {
            _signInState.postValue(ViewState.empty(context.getString(R.string.empty_account)))
        } else if (password.isNullOrEmpty()) {
            _signInState.postValue(ViewState.empty(context.getString(R.string.empty_password)))
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                val deviceId = context.dataStoreUserPref.getDeviceId().first()
                val fcmToken = context.dataStoreUserPref.getFcmToken().first()
                val results = userRepo.signIn(account, password, deviceId, fcmToken)
                when (results) {
                    is ResponseResults.Successful -> {
                        val signInInfo = results.data
                        val uuid: Long
                        val account: String
                        val accessToken: String
                        val refreshToken: String
                        val isFirst: Boolean
                        when (signInInfo.signState) {
                            SignState.SIGN_IN_SUCCESS -> {
                                signInInfo.user.also {
                                    uuid = 0
                                    account = it!!.account
                                    accessToken = signInInfo.accessToken!!
                                    refreshToken = signInInfo.refreshToken!!
                                    isFirst = signInInfo.isFirst!!
                                }
                            }
                            //SignState.SIGN_IN_FAILURE
                            else -> {
                                uuid = -1
                                account = ""
                                accessToken = ""
                                refreshToken = ""
                                isFirst = false
                            }
                        }
                        context.dataStoreUserPref.setDelegate(
                            uuid = uuid,
                            account = account,
                            accessToken = accessToken,
                            refreshToken = refreshToken,
                            deviceId = "deviceId001",
                            isFirst = isFirst
                        )

                        when (signInInfo.signState) {
                            SignState.SIGN_IN_SUCCESS -> {
                                _signInState.postValue(ViewState.data(signInInfo))
                            }
                            SignState.SIGN_IN_FAILURE -> {
                                _signInState.postValue(ViewState.empty(context.getString(R.string.error_account_password)))
                            }
                        }
                    }
                    is ResponseResults.ClientErrors -> {
                        _signInState.postValue(ViewState.error(results.e))
                    }
                    is ResponseResults.NetWorkError -> {
                        _signInState.postValue(ViewState.network(results.e))
                    }
                }

            }

        }

    }


}