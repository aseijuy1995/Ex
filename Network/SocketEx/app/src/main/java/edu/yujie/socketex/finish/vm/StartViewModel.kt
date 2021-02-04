package edu.yujie.socketex.finish.vm

import android.app.Application
import androidx.datastore.DataStore
import androidx.datastore.createDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import edu.yujie.socketex.UserBean
import edu.yujie.socketex.finish.base.viewModel.BaseAndroidViewModel
import edu.yujie.socketex.finish.bean.InitBean
import edu.yujie.socketex.finish.bean.UserSerializer
import edu.yujie.socketex.finish.inter.SignInState
import edu.yujie.socketex.finish.inter.IApiRepo
import edu.yujie.socketex.util.asLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class StartViewModel(application: Application, private val repo: IApiRepo) : BaseAndroidViewModel(application) {

    private val _initData = MutableLiveData<InitBean>()

    private val userDataStore: DataStore<UserBean> by lazy { context.createDataStore(fileName = "user.proto", serializer = UserSerializer) }

    private val _signInState = MutableLiveData<SignInState>()

    val signInState = _signInState.asLiveData()

    private val _user = MutableLiveData<UserBean>()

    val user = _user.asLiveData()

//    private val _toast = MutableLiveData<String>("")
//
//    val toast = _toast.asLiveData()

    fun getInitData(): LiveData<InitBean> {
        viewModelScope.launch(Dispatchers.Main) {
            _initData.value = repo.getInit()
        }
        return _initData.asLiveData()
    }

    fun checkSignIn() = viewModelScope.launch {
        userDataStore.data.collect {
            if (it.account.isEmpty() || it.authToken.isEmpty() || it.deviceToken.isEmpty()) {
                _signInState.value = SignInState.NOT_SIGN_IN
            } else {
                val user = repo.postCheckSignIn(account = it.account, authToken = it.authToken, deviceToken = it.deviceToken)

                if (user.account.isEmpty() || user.authToken.isEmpty() || user.deviceToken.isEmpty()) {
                    _signInState.value = SignInState.ERROR_ACT_PWD
                } else {
                    _signInState.value = SignInState.SIGN_IN
                }
            }
        }
    }


//        val settingDataStore: DataStore<User> by lazy {
////            context.createDataStore(
////                fileName = "user.proto",
////                serializer = UserSerializer
////            )
//        }
}
