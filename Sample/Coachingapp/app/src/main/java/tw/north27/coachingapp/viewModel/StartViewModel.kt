package tw.north27.coachingapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tw.north27.coachingapp.base.viewModel.BaseViewModel
import tw.north27.coachingapp.model.Data
import tw.north27.coachingapp.repository.inter.IStartRepository

class StartViewModel(val repo: IStartRepository) : BaseViewModel() {


    fun originate() {

        viewModelScope.launch {
            repo.getOriginateData()
        }
    }


    //
    val dataLiveData = MutableLiveData<Data>()

    fun getData() {
        viewModelScope.launch {
            val data = repo.getData()
            dataLiveData.postValue(data)
        }
    }

}