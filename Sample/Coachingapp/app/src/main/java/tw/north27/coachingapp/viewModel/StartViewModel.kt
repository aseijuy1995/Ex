package tw.north27.coachingapp.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yujie.prefmodule.dataStore.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import tw.north27.coachingapp.base.BaseAndroidViewModel
import tw.north27.coachingapp.ext2.asLiveData
import tw.north27.coachingapp.model.result.AppConfig
import tw.north27.coachingapp.model.result.SignInfo
import tw.north27.coachingapp.model.result.SignState
import tw.north27.coachingapp.module.http.ResponseResults
import tw.north27.coachingapp.module.http.Results
import tw.north27.coachingapp.repository.inter.IPublicRepository
import tw.north27.coachingapp.repository.inter.IUserRepository
import tw.north27.coachingapp.util2.ViewState

class StartViewModel(
    application: Application,
    private val publicRepo: IPublicRepository,
    private val userRepo: IUserRepository
) : BaseAndroidViewModel(application) {

    private val _appConfigState = MutableLiveData<ViewState<AppConfig>>(ViewState.Initial)

    val appConfigState = _appConfigState.asLiveData()

    fun getAppConfig(fcmToken: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _appConfigState.postValue(ViewState.load())
            context.dataStoreUserPref.setFcmToken(fcmToken)
            val results = publicRepo.getAppConfig(fcmToken)
            when (results) {
                is Results.Successful -> {
                    _appConfigState.postValue(ViewState.data(results.data!!))
                }
                is Results.ClientErrors -> {
                    _appConfigState.postValue(ViewState.error(results.e))
                }
                is Results.NetWorkError -> {
                    _appConfigState.postValue(ViewState.network(results.e))
                }
            }
        }
    }

    private val _signInState = MutableLiveData<ViewState<SignInfo>>(ViewState.Initial)

    val signInState = _signInState.asLiveData()

    fun checkSignIn() {
        viewModelScope.launch(Dispatchers.IO) {
            _signInState.postValue(ViewState.load())
            val account = context.dataStoreUserPref.getAccount().first()
            val deviceId = context.dataStoreUserPref.getDeviceId().first()
            val fcmToken = context.dataStoreUserPref.getFcmToken().first()

            if (account.isEmpty() || deviceId.isEmpty() || fcmToken.isEmpty()) {
                _signInState.postValue(ViewState.empty())
                return@launch
            }
            val results = userRepo.checkSignIn(account, deviceId, fcmToken)
            when (results) {
                is ResponseResults.Successful -> {
                    val signIn = results.data
                    val account: String
                    val accessToken: String
                    val refreshToken: String
                    val isFirst: Boolean
                    when (signIn.signState) {
                        SignState.SIGN_IN_SUCCESS -> {
                            signIn.user.also {
                                account = it!!.account
                                accessToken = signIn.accessToken!!
                                refreshToken = signIn.refreshToken!!
                                isFirst = signIn.isFirst!!
                            }
                        }
                        //SignState.SIGN_IN_FAILURE
                        else -> {
                            account = ""
                            accessToken = ""
                            refreshToken = ""
                            isFirst = false
                        }
                    }
                    context.dataStoreUserPref.setDelegate(
                        account = account,
                        accessToken = accessToken,
                        refreshToken = refreshToken,
                        isFirst = isFirst,
                    )
                    _signInState.postValue(ViewState.data(signIn))
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