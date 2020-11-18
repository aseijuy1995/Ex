package edu.yujie.hiltex.arch

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import edu.yujie.hiltex.AppResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HiltViewModel @ViewModelInject constructor(
    val repo: HiltRepository,
    @Assisted savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val TAG = javaClass.simpleName

    val appResult = liveData {
        emit(repo.postAppResult())
    }

    fun insertAppResult() = viewModelScope.launch(Dispatchers.IO) {
        val result = repo.insert()
        println("result:$result")
    }

    //viewModel & room
    fun getAppResultFromDatabase(): LiveData<AppResult> = repo.getAppResult()
}