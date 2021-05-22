package tw.north27.coachingapp.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yujie.basemodule.BaseAndroidViewModel
import com.yujie.prefmodule.dataStore.*
import com.yujie.pushmodule.fcm.FirebaseMsg
import com.yujie.utilmodule.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber
import tw.north27.coachingapp.ext2.asLiveData
import tw.north27.coachingapp.model.result.AppConfig
import tw.north27.coachingapp.model.result.SignIn
import tw.north27.coachingapp.model.result.SignInState
import tw.north27.coachingapp.module.http.ResponseResults
import tw.north27.coachingapp.module.http.Results
import tw.north27.coachingapp.repository.inter.IPublicRepository
import tw.north27.coachingapp.repository.nofinish.IUserRepository
import java.util.*

class StartViewModel(
    application: Application,
    private val publicRepo: IPublicRepository,
    private val userRepo: IUserRepository
) : BaseAndroidViewModel(application) {

    private val _appConfigState = MutableLiveData<ViewState<AppConfig>>(ViewState.Initial)

    val appConfigState = _appConfigState.asLiveData()

    fun getAppConfig() {
        viewModelScope.launch(Dispatchers.IO) {
            _appConfigState.postValue(ViewState.load())
            cxt.dataStoreUserPref.setFcmToken(FirebaseMsg.fcmToken!!)
            if (cxt.dataStoreUserPref.getUuid().first().isEmpty()) {
                val uuid = UUID.randomUUID().toString()
                Timber.i("UUID = $uuid")
                cxt.dataStoreUserPref.setUuid(uuid)
            }
            val uuid = cxt.dataStoreUserPref.getUuid().first()
            val results = publicRepo.getAppConfig(uuid, FirebaseMsg.fcmToken!!)
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

    private val _signInState = MutableLiveData<ViewState<SignIn>>(ViewState.Initial)

    val signInState = _signInState.asLiveData()

    fun checkSignIn() {
        viewModelScope.launch(Dispatchers.IO) {
            _signInState.postValue(ViewState.load())
            val uuid = cxt.dataStoreUserPref.getUuid().first()
            val account = cxt.dataStoreUserPref.getAccount().first()
            val accessToken = cxt.dataStoreUserPref.getAccessToken().first()
            val fcmToken = cxt.dataStoreUserPref.getFcmToken().first()

            if (account.isEmpty() || accessToken.isEmpty()) {
                _signInState.postValue(ViewState.empty())
            } else {
                val results = userRepo.checkSignIn(uuid, account, accessToken, fcmToken)
                when (results) {
                    is ResponseResults.Successful -> {
                        val signIn = results.data
                        val accountNew: String
                        val authNew: String
                        val accessTokenNew: String
                        val refreshTokenNew: String
                        val fcmTokenNew: String
                        val isFirstNew: Boolean
                        when (signIn.signInState) {
                            SignInState.SIGN_IN -> {
                                val signInInfo = signIn.signInInfo!!
                                val userInfo = signInInfo.userInfo!!
                                accountNew = userInfo.account
                                authNew = userInfo.auth.toString()
                                accessTokenNew = signInInfo.accessToken!!
                                refreshTokenNew = signInInfo.refreshToken!!
                                fcmTokenNew = signInInfo.fcmToken!!
                                isFirstNew = signInInfo.isFirst!!
                            }
                            SignInState.SIGN_OUT -> {
                                accountNew = ""
                                authNew = ""
                                accessTokenNew = ""
                                refreshTokenNew = ""
                                isFirstNew = false
                            }
                        }
                        cxt.dataStoreUserPref.setDelegate(
                            account = accountNew,
                            auth = authNew,
                            accessToken = accessTokenNew,
                            refreshToken = refreshTokenNew,
                            isFirst = isFirstNew,
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

}