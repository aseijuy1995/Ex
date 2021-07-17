package tw.north27.coachingapp.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yujie.core_lib.base.BaseAndroidViewModel
import com.yujie.core_lib.http.Results
import com.yujie.core_lib.pref.getId
import com.yujie.core_lib.pref.userPref
import com.yujie.core_lib.util.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import tw.north27.coachingapp.model.ClientInfo
import tw.north27.coachingapp.model.request.PairRequest
import tw.north27.coachingapp.repository.IActionRepository

class EducationSelectorViewModel(
    application: Application,
    private val actionRepo: IActionRepository
) : BaseAndroidViewModel(application) {

    val teacherPairState = MutableLiveData<ViewState<ClientInfo>>(ViewState.initial())

    fun fetchTeacherPair(unitId: Long) = viewModelScope.launch(Dispatchers.IO) {
        teacherPairState.postValue(ViewState.load())
        val clientId = cxt.userPref.getId().first()
        val results = actionRepo.fetchTeacherPair(
            pairRequest = PairRequest(
                clientId = clientId,
                unitId = unitId
            )
        )
        when (results) {
            is Results.Successful -> {
                val clientInfo = results.data
                if (clientInfo == null)
                    teacherPairState.postValue(ViewState.empty())
                else {
                    teacherPairState.postValue(ViewState.data(clientInfo))
                }
            }
            is Results.ClientErrors -> {
                teacherPairState.postValue(ViewState.error(results.e))
            }
            is Results.NetWorkError -> {
                teacherPairState.postValue(ViewState.network(results.e))
            }
        }


    }

}