package tw.north27.coachingapp.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yujie.basemodule.BaseAndroidViewModel
import com.yujie.utilmodule.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tw.north27.coachingapp.ext2.asLiveData
import tw.north27.coachingapp.model.Chapter
import tw.north27.coachingapp.model.Grade
import tw.north27.coachingapp.model.Subject
import tw.north27.coachingapp.model.UserInfo
import tw.north27.coachingapp.module.http.Results
import tw.north27.coachingapp.repository.inter.IPublicRepository

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
        viewModelScope.launch(Dispatchers.IO) {
            _teacherInfoState.postValue(ViewState.load())
            val results = publicRepo.getLoadTeacher(gradeId, subjectId, chapterId)
            when (results) {
                is Results.Successful -> {
                    if (results.data.isEmpty())
                        _teacherInfoState.postValue(ViewState.empty())
                    else
                        _teacherInfoState.postValue(ViewState.data(results.data!!))
                }
                is Results.ClientErrors -> {
                    _teacherInfoState.postValue(ViewState.error(results.e))
                }
                is Results.NetWorkError -> {
                    _teacherInfoState.postValue(ViewState.network(results.e))
                }
            }
        }
    }

    private val _gradeListState = MutableLiveData<ViewState<List<Grade>>>(ViewState.Initial)

    val gradeListState = _gradeListState.asLiveData()

    fun getGrade() {
        viewModelScope.launch(Dispatchers.IO) {
            _gradeListState.postValue(ViewState.load())
            val results = publicRepo.getGrade()
            when (results) {
                is Results.Successful -> {
                    if (results.data.isEmpty())
                        _gradeListState.postValue(ViewState.empty())
                    else
                        _gradeListState.postValue(ViewState.data(results.data!!))
                }
                is Results.ClientErrors -> {
                    _gradeListState.postValue(ViewState.error(results.e))
                }
                is Results.NetWorkError -> {
                    _gradeListState.postValue(ViewState.network(results.e))
                }
            }
        }
    }

    private val _subjectListState = MutableLiveData<ViewState<List<Subject>>>(ViewState.Initial)

    val subjectListState = _subjectListState.asLiveData()

    fun getSubject(gradeId: Long? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            _subjectListState.postValue(ViewState.load())
            val results = publicRepo.getSubject(gradeId)
            when (results) {
                is Results.Successful -> {
                    if (results.data.isEmpty())
                        _subjectListState.postValue(ViewState.empty())
                    else
                        _subjectListState.postValue(ViewState.data(results.data!!))
                }
                is Results.ClientErrors -> {
                    _subjectListState.postValue(ViewState.error(results.e))
                }
                is Results.NetWorkError -> {
                    _subjectListState.postValue(ViewState.network(results.e))
                }
            }
        }
    }

    private val _chapterListState = MutableLiveData<ViewState<List<Chapter>>>(ViewState.Initial)

    val chapterListState = _chapterListState.asLiveData()

    fun getChapter(gradeId: Long? = null, subjectId: Long? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            _chapterListState.postValue(ViewState.load())
            val results = publicRepo.getChapter(gradeId, subjectId)
            when (results) {
                is Results.Successful -> {
                    if (results.data.isEmpty())
                        _chapterListState.postValue(ViewState.empty())
                    else
                        _chapterListState.postValue(ViewState.data(results.data!!))
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

}