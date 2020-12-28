package edu.yujie.lohasapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.yujie.lohasapp.AdvertBean
import edu.yujie.lohasapp.config.adImagePath
import edu.yujie.lohasapp.config.baseUrl
import edu.yujie.lohasapp.repo.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val repo: HomeRepository) : ViewModel() {
    private val mAdverts: MutableLiveData<List<AdvertBean>> by lazy { MutableLiveData<List<AdvertBean>>() }

    fun getAdvert(): LiveData<List<AdvertBean>> {
        viewModelScope.launch(Dispatchers.IO) {
            val adverts = repo.getAdvert()
            adverts.mapIndexed { index, advertBean ->
                adverts[index].img = "$baseUrl$adImagePath${advertBean.img}"
            }
            mAdverts.postValue(adverts)
        }
        return mAdverts
    }
}