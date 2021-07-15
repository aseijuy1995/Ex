package tw.north27.coachingapp.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yujie.core_lib.UserPref
import com.yujie.core_lib.base.BaseAndroidViewModel
import com.yujie.core_lib.ext.asLiveData
import com.yujie.core_lib.pref.setUserPref
import com.yujie.core_lib.pref.userPref
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tw.north27.coachingapp.repository.IUserRepository

class SignOutViewModel(application: Application, val userRepo: IUserRepository) : BaseAndroidViewModel(application) {

    private val _isSignOut = MutableLiveData<Boolean>(false)

    val isSignOut = _isSignOut.asLiveData()

    fun signOut() = viewModelScope.launch(Dispatchers.IO) {
        cxt.userPref.setUserPref(
            account = "",
            expiresTime = 0L,
            accessToken = "",
            refreshToken = "",
            isFirst = false,
            auth = UserPref.Authority.NONE,
        )
        _isSignOut.postValue(true)
    }

}