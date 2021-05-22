package tw.north27.coachingapp.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yujie.basemodule.BaseAndroidViewModel
import com.yujie.utilmodule.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tw.north27.coachingapp.ext2.asLiveData
import tw.north27.coachingapp.model.result.UserInfo
import tw.north27.coachingapp.module.http.Results
import tw.north27.coachingapp.repository.inter.IPublicRepository

class MainHomeViewModel(
    application: Application,
    private val publicRepo: IPublicRepository,
) : BaseAndroidViewModel(application) {

    private val _teacherInfoState = MutableLiveData<ViewState<List<UserInfo>>>(ViewState.Initial)

    val teacherInfoState = _teacherInfoState.asLiveData()

    fun getLoadTeacher() {
        viewModelScope.launch(Dispatchers.IO) {
            _teacherInfoState.postValue(ViewState.load())
            val results = publicRepo.getLoadTeacher()
            when (results) {
                is Results.Successful -> {
                    if (results.data.isEmpty())
                        _teacherInfoState.postValue(ViewState.empty())
                    else
                        _teacherInfoState.postValue(ViewState.data(results.data!!))
                }
                is Results.ClientErrors -> {
                    _teacherInfoState.postValue(ViewState.error(results.e))
                }
                is Results.NetWorkError -> {
                    _teacherInfoState.postValue(ViewState.network(results.e))
                }
            }
        }
    }

}