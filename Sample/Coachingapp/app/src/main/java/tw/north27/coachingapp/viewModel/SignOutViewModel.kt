package tw.north27.coachingapp.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yujie.utilmodule.UserPref
import com.yujie.utilmodule.base.BaseAndroidViewModel
import com.yujie.utilmodule.ext.asLiveData
import com.yujie.utilmodule.pref.setUserPref
import com.yujie.utilmodule.pref.userPref
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tw.north27.coachingapp.repository.IUserRepository

class SignOutViewModel(application: Application, val userRepo: IUserRepository) : BaseAndroidViewModel(application) {

    private val _isSignOut = MutableLiveData<Boolean>(false)

    val isSignOut = _isSignOut.asLiveData()

    fun signOut() = viewModelScope.launch(Dispatchers.IO) {
        cxt.userPref.setUserPref(
            account = "",
            expireTime = 0L,
            accessToken = "",
            refreshToken = "",
            isFirst = false,
            auth = UserPref.Authority.NONE,
        )
        _isSignOut.postValue(true)
    }

}