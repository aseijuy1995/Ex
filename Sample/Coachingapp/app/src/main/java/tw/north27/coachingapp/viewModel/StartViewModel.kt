package tw.north27.coachingapp.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber
import tw.north27.coachingapp.base.BaseAndroidViewModel
import tw.north27.coachingapp.ext.asLiveData
import tw.north27.coachingapp.model.result.AppConfig
import tw.north27.coachingapp.model.result.SignInInfo
import tw.north27.coachingapp.model.result.SignInState
import tw.north27.coachingapp.module.http.ResponseResults
import tw.north27.coachingapp.module.http.Results
import tw.north27.coachingapp.module.pref.UserModule
import tw.north27.coachingapp.repository.inter.IPublicRepository
import tw.north27.coachingapp.repository.inter.IUserRepository
import tw.north27.coachingapp.util.ViewState

class StartViewModel(
    application: Application,
    private val publicRepo: IPublicRepository,
    private val userRepo: IUserRepository
) : BaseAndroidViewModel(application) {

    private val userModule = UserModule(application)

    private val _appConfigState = MutableLiveData<ViewState<AppConfig>>(ViewState.Load)

    val appConfigState = _appConfigState.asLiveData()

    fun getAppConfig(fcmToken: String) {
        viewModelScope.launch(Dispatchers.IO) {
            userModule.setValue(fcmToken = fcmToken)
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

    private val _signInState = MutableLiveData<ViewState<SignInInfo>>(ViewState.Load)

    val signInState = _signInState.asLiveData()

    fun checkSignIn() {
        viewModelScope.launch(Dispatchers.IO) {
            val user = userModule.getValue { it }.first()
            Timber.d("account = ${user.account}, deviceId = ${user.deviceId}, fcmToken = ${user.fcmToken}")
            if (user.account.isEmpty() || user.deviceId.isEmpty() || user.fcmToken.isEmpty()) {
                _signInState.postValue(ViewState.empty())
                return@launch
            }
            val results = userRepo.checkSignIn(user.account, user.deviceId, user.fcmToken)
            when (results) {
                is ResponseResults.Successful -> {
                    val signIn = results.data
                    val account: String
                    val accessToken: String
                    val refreshToken: String
                    val isFirst: Boolean
                    when (signIn.signInState) {
                        SignInState.SUCCESS -> {
                            signIn.user.also {
                                account = it!!.account
                                accessToken = signIn.accessToken!!
                                refreshToken = signIn.refreshToken!!
                                isFirst = signIn.isFirst!!
                            }
                        }
                        SignInState.FAILURE -> {
                            account = ""
                            accessToken = ""
                            refreshToken = ""
                            isFirst = false
                        }
                    }
                    userModule.setValue(
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