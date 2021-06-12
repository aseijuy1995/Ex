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
import tw.north27.coachingapp.model.*
import tw.north27.coachingapp.model.Unit
import tw.north27.coachingapp.repository.IPublicRepository

class MainHomeViewModel(
    application: Application,
    private val publicRepo: IPublicRepository,
) : BaseAndroidViewModel(application) {

    private val _teacherInfoState = MutableLiveData<ViewState<List<UserInfo>>>(ViewState.Initial)

    val teacherInfoState = _teacherInfoState.asLiveData()

    fun getLoadTeacher(
        gradeId: Long? = null,
        subjectId: Long? = null,
        chapterId: Long? = null
    ) {
//        viewModelScope.launch(Dispatchers.IO) {
//            _teacherInfoState.postValue(ViewState.load())
//            val results = publicRepo.getLoadTeacher(gradeId, subjectId, chapterId)
//            when (results) {
//                is Results.Successful<List<UserInfo>> -> {
//                    if (results.data.isEmpty())
//                        _teacherInfoState.postValue(ViewState.empty())
//                    else
//                        _teacherInfoState.postValue(ViewState.data(results.data!!))
//                }
//                is Results.ClientErrors -> {
//                    _teacherInfoState.postValue(ViewState.error(results.e))
//                }
//                is Results.NetWorkError -> {
//                    _teacherInfoState.postValue(ViewState.network(results.e))
//                }
//            }
//        }
    }

    val defaultEducation = Education(id = null, text = "預設")
    val defaultGradle = Grade(id = null, text = "預設", educationId = null)
    val defaultSubject = Subject(id = null, text = "預設", educationIdList = listOf(), gradleIdList = listOf())
    val defaultUnit = Unit(id = null, text = "預設", educationId = null, gradeId = null, subjectId = null)

    private val _educationListState = MutableLiveData<ViewState<List<Education>>>(ViewState.initial())

    val educationListState = _educationListState.asLiveData()

    fun getEducationList() = viewModelScope.launch(Dispatchers.IO) {
        _gradeListState.postValue(ViewState.load())
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

    fun getGradeList(educationId: Long? = 0) = viewModelScope.launch(Dispatchers.IO) {
        _gradeListState.postValue(ViewState.load())
        val results = publicRepo.getGradeList(educationId)
        when (results) {
            is Results.Successful<List<Grade>> -> {
                if (results.data.isEmpty())
                    _gradeListState.postValue(ViewState.empty())
                else
                    _gradeListState.postValue(ViewState.data(results.data))
            }
            is Results.ClientErrors -> {
                _gradeListState.postValue(ViewState.error(results.e))
            }
            is Results.NetWorkError -> {
                _gradeListState.postValue(ViewState.network(results.e))
            }
        }
    }

    private val _subjectListState = MutableLiveData<ViewState<List<Subject>>>(ViewState.initial())

    val subjectListState = _subjectListState.asLiveData()

    fun getSubjectList(educationId: Long? = 0, gradeId: Long? = 0) = viewModelScope.launch(Dispatchers.IO) {
        _subjectListState.postValue(ViewState.load())
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

    private val _chapterListState = MutableLiveData<ViewState<List<Unit>>>(ViewState.initial())

    val chapterListState = _chapterListState.asLiveData()

    fun getUnitList(educationId: Long? = 0, gradeId: Long? = 0, subjectId: Long? = 0) = viewModelScope.launch(Dispatchers.IO) {
        _chapterListState.postValue(ViewState.load())
        val results = publicRepo.getUnitList(educationId = educationId, gradeId = gradeId, subjectId = subjectId)
        when (results) {
            is Results.Successful<List<Unit>> -> {
                if (results.data.isEmpty())
                    _chapterListState.postValue(ViewState.empty())
                else
                    _chapterListState.postValue(ViewState.data(results.data))
            }
            is Results.ClientErrors -> {
                _chapterListState.postValue(ViewState.error(results.e))
            }
            is Results.NetWorkError -> {
                _chapterListState.postValue(ViewState.network(results.e))
            }
        }
    }


}