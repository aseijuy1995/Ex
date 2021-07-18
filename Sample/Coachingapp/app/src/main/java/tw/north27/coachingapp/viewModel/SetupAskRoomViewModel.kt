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
import tw.north27.coachingapp.model.AskRoom
import tw.north27.coachingapp.model.request.SetupAskRoomRequest
import tw.north27.coachingapp.repository.IAskRoomRepository

class SetupAskRoomViewModel(
    application: Application,
    private val askRoomRepo: IAskRoomRepository
) : BaseAndroidViewModel(application) {

    private val _askRoomState = MutableLiveData<ViewState<AskRoom>>(ViewState.initial())

    val askRoomState = _askRoomState.asLiveData()

    fun setupAskRoom(otherClientId: String, unitId: Long) = viewModelScope.launch(Dispatchers.IO) {
        _askRoomState.postValue(ViewState.load())
        val clientId = cxt.userPref.getId().first()
        val results = askRoomRepo.setupAskRoom(
            setupAskRoomRequest = SetupAskRoomRequest(
                selfClientId = clientId,
                otherClientId = otherClientId,
                unitId = unitId
            )
        )
        when (results) {
            is Results.Successful<AskRoom> -> {
                val askRoomResponse = results.data
                _askRoomState.postValue(ViewState.data(askRoomResponse))
            }
            is Results.ClientErrors -> {
                _askRoomState.postValue(ViewState.error(results.e))
            }
            is Results.NetWorkError -> {
                _askRoomState.postValue(ViewState.network(results.e))
            }
        }
    }
}