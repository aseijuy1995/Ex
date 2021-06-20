package tw.north27.coachingapp.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yujie.utilmodule.base.BaseAndroidViewModel
import com.yujie.utilmodule.ext.asLiveData
import com.yujie.utilmodule.http.Results
import com.yujie.utilmodule.util.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tw.north27.coachingapp.model.Education
import tw.north27.coachingapp.model.Grade
import tw.north27.coachingapp.model.Subject
import tw.north27.coachingapp.model.Unit
import tw.north27.coachingapp.repository.IPublicRepository

class PublicViewModel(
    application: Application,
    private val publicRepo: IPublicRepository
) : BaseAndroidViewModel(application) {

    val defaultEducation = Education(id = null, text = "預設")

    val defaultGradle = Grade(id = null, text = "預設", educationId = null)

    val defaultSubject = Subject(id = null, text = "預設", educationIdList = listOf(), gradleIdList = listOf())

    val defaultUnit = Unit(id = null, text = "預設", educationId = null, gradeId = null, subjectId = null)

    private val _educationListState: MutableLiveData<ViewState<List<Education>>> by lazy {
        getEducationList()
        MutableLiveData<ViewState<List<Education>>>(ViewState.load())
    }

    val educationListState = _educationListState.asLiveData()

    private fun getEducationList() = viewModelScope.launch(Dispatchers.IO) {
        val results = publicRepo.getEducationList()
        when (results) {
            is Results.Successful<List<Education>> -> {
                if (results.data.isEmpty())
                    _educationListState.postValue(ViewState.empty())
                else
                    _educationListState.postValue(ViewState.data(results.data))
            }
            is Results.ClientErrors -> {
                _educationListState.postValue(ViewState.error(results.e))
            }
            is Results.NetWorkError -> {
                _educationListState.postValue(ViewState.network(results.e))
            }
        }
    }

    private val _gradeListState = MutableLiveData<ViewState<List<Grade>>>(ViewState.initial())

    val gradeListState = _gradeListState.asLiveData()

    suspend fun getGradeList(educationId: Long? = 0) = withContext(Dispatchers.IO) {
        _gradeListState.postValue(ViewState.load())
        val results = publicRepo.getGradeList(educationId = educationId)
        when (results) {
            is Results.Successful<List<Grade>> -> {
                if (results.data.isEmpty()) {
                    _gradeListState.postValue(ViewState.empty())
                    ViewState.empty()
                } else {
                    _gradeListState.postValue(ViewState.data(results.data))
                    ViewState.data(results.data)
                }
            }
            is Results.ClientErrors -> {
                _gradeListState.postValue(ViewState.error(results.e))
                ViewState.error(results.e)
            }
            is Results.NetWorkError -> {
                _gradeListState.postValue(ViewState.network(results.e))
                ViewState.network(results.e)
            }
        }
    }

    private val _subjectListState: MutableLiveData<ViewState<List<Subject>>> by lazy {
        getSubjectList()
        MutableLiveData<ViewState<List<Subject>>>(ViewState.load())
    }

    val subjectListState = _subjectListState.asLiveData()

    private fun getSubjectList(educationId: Long? = 0, gradeId: Long? = 0) = viewModelScope.launch(Dispatchers.IO) {
        val results = publicRepo.getSubjectList(educationId = educationId, gradeId = gradeId)
        when (results) {
            is Results.Successful<List<Subject>> -> {
                if (results.data.isEmpty())
                    _subjectListState.postValue(ViewState.empty())
                else
                    _subjectListState.postValue(ViewState.data(results.data))
            }
            is Results.ClientErrors -> {
                _subjectListState.postValue(ViewState.error(results.e))
            }
            is Results.NetWorkError -> {
                _subjectListState.postValue(ViewState.network(results.e))
            }
        }
    }

    private val _unitListState: MutableLiveData<ViewState<List<Unit>>> by lazy {
        getUnitList()
        MutableLiveData<ViewState<List<Unit>>>(ViewState.load())
    }

    val unitListState = _unitListState.asLiveData()

    private fun getUnitList(educationId: Long? = 0, gradeId: Long? = 0, subjectId: Long? = 0) = viewModelScope.launch(Dispatchers.IO) {
        val results = publicRepo.getUnitList(educationId = educationId, gradeId = gradeId, subjectId = subjectId)
        when (results) {
            is Results.Successful<List<tw.north27.coachingapp.model.Unit>> -> {
                if (results.data.isEmpty())
                    _unitListState.postValue(ViewState.empty())
                else
                    _unitListState.postValue(ViewState.data(results.data))
            }
            is Results.ClientErrors -> {
                _unitListState.postValue(ViewState.error(results.e))
            }
            is Results.NetWorkError -> {
                _unitListState.postValue(ViewState.network(results.e))
            }
        }
    }
}