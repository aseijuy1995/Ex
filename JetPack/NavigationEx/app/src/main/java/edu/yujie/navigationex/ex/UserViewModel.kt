package edu.yujie.navigationex.ex

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    private var mUser: MutableLiveData<User?> = MutableLiveData(null)

    var user: LiveData<User?> = mUser

    fun login(account: String, password: String): LiveData<LoginResult> {
        val result = MutableLiveData<LoginResult>()
        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password))
            result.postValue(LoginResult.Empty)
        else if (account == "account" && password == "password") {
            viewModelScope.launch {
                delay(1500)
                user = MutableLiveData(User("username"))
                result.postValue(LoginResult.Success)
            }

        } else {
            viewModelScope.launch {
                delay(1500L)
                result.postValue(LoginResult.Failed)
            }
        }
        return result
    }
}
