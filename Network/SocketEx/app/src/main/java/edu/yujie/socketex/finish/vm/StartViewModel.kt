package edu.yujie.socketex.finish.vm

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import edu.yujie.socketex.UserBean
import edu.yujie.socketex.finish.base.viewModel.BaseAndroidViewModel
import edu.yujie.socketex.finish.inter.IApiRepo
import edu.yujie.socketex.finish.inter.IUserRepo
import edu.yujie.socketex.finish.inter.SignInStatus
import edu.yujie.socketex.finish.result.InitResult
import edu.yujie.socketex.util.asLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class StartViewModel(application: Application, private val repo: IApiRepo, private val userRepo: IUserRepo) : BaseAndroidViewModel(application) {

    private val _initData = MutableLiveData<InitResult>()

    private val _signInStatus = MutableLiveData<SignInStatus>()

    val signInStatus = _signInStatus.asLiveData()

    private val _user = MutableLiveData<UserBean>()

    val user = _user.asLiveData()

    fun getInitData(): LiveData<InitResult> {
        viewModelScope.launch(Dispatchers.Main) {
            _initData.value = repo.getInit()
        }
        return _initData.asLiveData()
    }

    fun checkSignInState() = viewModelScope.launch {
        userRepo.signInDataStore.data.collect {
            if (it.account.isEmpty() || it.authToken.isEmpty()) {
                _signInStatus.value = SignInStatus.NOT_SIGN_IN
            } else {
                repo.postCheckSignIn(it).run {
                    if (account.isEmpty() || authToken.isEmpty()) {
                        _signInStatus.value = SignInStatus.EXPIRED_TOKEN
                        _signInStatus.value = SignInStatus.EXPIRED_TOKEN
                    } else {
                        _signInStatus.value = SignInStatus.SIGN_IN
                    }
                }
            }
        }
    }

    fun checkSignIn(account: String, password: String) {

    }


//        val settingDataStore: DataStore<User> by lazy {
////            context.createDataStore(
////                fileName = "ProtoDataStoreStorage.proto",
////                serializer = UserSerializer
////            )
//        }
}
