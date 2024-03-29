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
import tw.north27.coachingapp.model.request.SetupAskRoomRequest
import tw.north27.coachingapp.model.response.AskRoomResponse
import tw.north27.coachingapp.repository.IAskRoomRepository

class TeacherDetailViewModel(
    application: Application,
    private val askRoomRepo: IAskRoomRepository
) : BaseAndroidViewModel(application) {

    private val _findAskRoomState = MutableLiveData<ViewState<AskRoomResponse>>(ViewState.initial())

    val findAskRoomState = _findAskRoomState.asLiveData()

    fun findAskRoom(otherClientId: String, unitId: Long) = viewModelScope.launch(Dispatchers.IO) {
        _findAskRoomState.postValue(ViewState.load())
        val clientId = cxt.userPref.getId().first()
        val results = askRoomRepo.findAskRoom(
            setupAskRoomRequest = SetupAskRoomRequest(
                selfClientId = clientId,
                otherClientId = otherClientId,
                unitId = unitId
            )
        )
        when (results) {
            is Results.Successful<AskRoomResponse> -> {
                val askRoomResponse = results.data
                _findAskRoomState.postValue(ViewState.data(askRoomResponse))
            }
            is Results.ClientErrors -> {
                _findAskRoomState.postValue(ViewState.error(results.e))
            }
            is Results.NetWorkError -> {
                _findAskRoomState.postValue(ViewState.network(results.e))
            }
        }
    }
}