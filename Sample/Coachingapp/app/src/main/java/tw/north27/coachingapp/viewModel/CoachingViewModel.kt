package tw.north27.coachingapp.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yujie.utilmodule.base.BaseAndroidViewModel
import com.yujie.utilmodule.ext.asLiveData
import com.yujie.utilmodule.http.Results
import com.yujie.utilmodule.pref.getAccount
import com.yujie.utilmodule.pref.userPref
import com.yujie.utilmodule.util.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import tw.north27.coachingapp.model.ClientInfo
import tw.north27.coachingapp.model.request.TeacherRequest
import tw.north27.coachingapp.repository.IActionRepository

class CoachingViewModel(
    application: Application,
    private val actionRepo: IActionRepository
) : BaseAndroidViewModel(application) {

    private val _teacherListState = MutableLiveData<ViewState<List<ClientInfo>>>(ViewState.initial())

    val teacherListState = _teacherListState.asLiveData()

    fun fetchTeacherList(
        educationId: Long? = null,
        gradeId: Long? = null,
        subjectId: Long? = null,
        unitId: Long? = null,
        index: Int,
        num: Int
    ) = viewModelScope.launch(Dispatchers.IO) {
        _teacherListState.postValue(ViewState.load())
        val account = cxt.userPref.getAccount().first()
        val results = actionRepo.fetchTeacherList(
            TeacherRequest(
                clientId = account,
                educationLevelId = educationId,
                gradeId = gradeId,
                subjectId = subjectId,
                unitId = unitId,
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