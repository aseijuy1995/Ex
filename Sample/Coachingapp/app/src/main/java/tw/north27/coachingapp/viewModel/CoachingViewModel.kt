package tw.north27.coachingapp.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yujie.core_lib.base.BaseAndroidViewModel
import com.yujie.core_lib.ext.asLiveData
import com.yujie.core_lib.http.Results
import com.yujie.core_lib.pref.getId
import com.yujie.core_lib.pref.userPref
import com.yujie.core_lib.util.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import tw.north27.coachingapp.model.ClientInfo
import tw.north27.coachingapp.model.request.TeacherRequest
import tw.north27.coachingapp.repository.IClientRepository

class CoachingViewModel(
    application: Application,
    private val clientRepo: IClientRepository
) : BaseAndroidViewModel(application) {

    private val _teacherListState = MutableLiveData<ViewState<List<ClientInfo>>>(ViewState.initial())

    val teacherListState = _teacherListState.asLiveData()

    fun fetchTeacherList(
        educationLevelId: Long? = null,
        gradeId: Long? = null,
        subjectId: Long? = null,
        unitTypeId: Long? = null,
        index: Int,
        num: Int
    ) = viewModelScope.launch(Dispatchers.IO) {
        _teacherListState.postValue(ViewState.load())
        val clientId = cxt.userPref.getId().first()
        val results = clientRepo.fetchTeacherList(
            TeacherRequest(
                clientId = clientId,
                educationLevelId = if (educationLevelId != -1L) educationLevelId else null,
                gradeId = if (gradeId != -1L) gradeId else null,
                subjectId = if (subjectId != -1L) subjectId else null,
                unitTypeId = if (unitTypeId != -1L) unitTypeId else null,
                index = index,
                num = num
            )
        )
        when (results) {
            is Results.Successful<List<ClientInfo>> -> {
                if (results.data.isEmpty())
                    _teacherListState.postValue(ViewState.empty())
                else
                    _teacherListState.postValue(ViewState.data(results.data))
            }
            is Results.ClientErrors -> {
                _teacherListState.postValue(ViewState.error(results.e))
            }
            is Results.NetWorkError -> {
                _teacherListState.postValue(ViewState.network(results.e))
            }
        }
    }

}