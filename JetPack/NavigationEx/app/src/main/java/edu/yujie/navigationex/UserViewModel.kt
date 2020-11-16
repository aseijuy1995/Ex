package edu.yujie.navigationex

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.delay

/**
 * @author YuJie on 2020/11/13
 * @describe 說明
 * @param 參數
 */
class UserViewModel : ViewModel() {

    val user = MutableLiveData<UserBean?>(null)

    fun login(account: String, password: String) = liveData {
        delay(1000)
        if (account == "account" && password == "password")
            emit(LoginResult.Success)
        else
            emit(LoginResult.Failed)
    }
}

enum class LoginResult {
    Success, Failed
}