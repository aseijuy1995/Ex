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
import tw.north27.coachingapp.BuildConfig
import tw.north27.coachingapp.R
import tw.north27.coachingapp.model.ClientInfo
import tw.north27.coachingapp.model.Gender
import tw.north27.coachingapp.model.request.AppConfigRequest
import tw.north27.coachingapp.model.request.ClientRequest
import tw.north27.coachingapp.model.response.*
import tw.north27.coachingapp.repository.IClientRepository
import tw.north27.coachingapp.repository.IPublicRepository

class PublicViewModel(
    application: Application,
    private val publicRepo: IPublicRepository,
    private val clientRepo: IClientRepository,
) : BaseAndroidViewModel(application) {

    private val _bgRes: MutableLiveData<Int> by lazy {
        val list = listOf<Int>(
            R.drawable.ic_launch_bg1,
            R.drawable.ic_launch_bg2,
            R.drawable.ic_launch_bg3,
            R.drawable.ic_launch_bg4,
            R.drawable.ic_launch_bg5,
            R.drawable.ic_launch_bg6,
            R.drawable.ic_launch_bg7,
            R.drawable.ic_launch_bg8,
            R.drawable.ic_launch_bg9,
            R.drawable.ic_launch_bg10,
        )
        MutableLiveData<Int>(list[list.indices.random()])
    }

    val bgRes = _bgRes.asLiveData()

    private val _appConfigState = MutableLiveData<ViewState<AppConfig>>(ViewState.initial())

    val appConfigState = _appConfigState.asLiveData()

    fun fetchAppConfig() = viewModelScope.launch(Dispatchers.IO) {
        _appConfigState.postValue(ViewState.load())
        val results = publicRepo.fetchAppConfig(
            appConfigRequest = AppConfigRequest(
                deviceType = BuildConfig.DEVICE_TYPE
            )
        )
        when (results) {
            is Results.Successful<AppConfig> -> {
                _appConfigState.postValue(ViewState.data(results.data))
            }
            is Results.ClientErrors -> {
                _appConfigState.postValue(ViewState.error(results.e))
            }
            is Results.NetWorkError -> {
                _appConfigState.postValue(ViewState.network(results.e))
            }
        }
    }

    private val _educationState = MutableLiveData<ViewState<Education>>(ViewState.initial())

    val educationState = _educationState.asLiveData()

    fun fetchEducation() = viewModelScope.launch(Dispatchers.IO) {
        val results = publicRepo.fetchEducation()
        when (results) {
            is Results.Successful<Education> -> {
                val educationData = results.data
                if (educationData.educationLevelList.isNotEmpty()
                    && educationData.gradeList.isNotEmpty()
                    && educationData.subjectList.isNotEmpty()
                    && educationData.unitTypeList.isNotEmpty()
                )
                    _educationState.postValue(ViewState.data(results.data))
                else
                    _educationState.postValue(ViewState.empty())
            }
            is Results.ClientErrors -> {
                _educationState.postValue(ViewState.error(results.e))
            }
            is Results.NetWorkError -> {
                _educationState.postValue(ViewState.network(results.e))
            }
        }
    }

    private val _educationLevelList = MutableLiveData<List<EducationLevel>>()

    val educationLevelList = _educationLevelList.asLiveData()

    fun setEducationLevelList(educationLevelList: List<EducationLevel>) = _educationLevelList.postValue(educationLevelList)

    private val _gradeList = MutableLiveData<List<Grade>>()

    val gradeList = _gradeList.asLiveData()

    fun setGradeList(gradeList: List<Grade>) = _gradeList.postValue(gradeList)

    private val _subjectList = MutableLiveData<List<Subject>>()

    val subjectList = _subjectList.asLiveData()

    fun setSubjectList(subjectList: List<Subject>) = _subjectList.postValue(subjectList)

    private val _unitList = MutableLiveData<List<UnitType>>()

    val unitList = _unitList.asLiveData()

    fun setUnitList(unitList: List<UnitType>) = _unitList.postValue(unitList)

    val defaultEducation = EducationLevel(id = -1, name = cxt.getString(R.string.df))

    val defaultGradle = Grade(id = -1, name = cxt.getString(R.string.df), educationLevelId = -1)

    val defaultSubject = Subject(id = -1, name = cxt.getString(R.string.df), educationLevelIdList = emptyList(), gradeIdList = emptyList())

    val defaultUnit = UnitType(id = -1, name = cxt.getString(R.string.df), educationLevelId = -1, gradeId = -1, subjectId = -1)

    //
    private val _personalBgRes: MutableLiveData<Int> by lazy {
        val list = listOf<Int>(
            R.drawable.ic_personal_bg1,
            R.drawable.ic_personal_bg2,
            R.drawable.ic_personal_bg3,
            R.drawable.ic_personal_bg4,
            R.drawable.ic_personal_bg5,
            R.drawable.ic_personal_bg6,
            R.drawable.ic_personal_bg7,
            R.drawable.ic_personal_bg8,
            R.drawable.ic_personal_bg9
        )
        MutableLiveData(list[list.indices.random()])
    }

    val personalBgRes = _personalBgRes.asLiveData()

    private val _userState = MutableLiveData<ViewState<ClientInfo>>(ViewState.initial())

    val userState = _userState.asLiveData()

    fun fetchUser() = viewModelScope.launch(Dispatchers.IO) {
        _userState.postValue(ViewState.load())
        val clientId = cxt.userPref.getId().first()
        if (clientId.isEmpty()) {
            _userState.postValue(ViewState.empty())
        } else {
            val results = clientRepo.fetchClient(clientRequest = ClientRequest(clientId = clientId))
            when (results) {
                is Results.Successful<ClientInfo> -> {
                    val userInfo = results.data
                    _userState.postValue(ViewState.data(userInfo))
                }
                is Results.ClientErrors -> {
                    _userState.postValue(ViewState.error(results.e))
                }
                is Results.NetWorkError -> {
                    _userState.postValue(ViewState.network(results.e))
                }
            }
        }
    }

    val defaultScore = -1.0

    private val _genderList = MutableLiveData<List<Gender>>(listOf(Gender.MALE, Gender.FEMALE))

    val genderList = _genderList.asLiveData()

    private val _scoreList = MutableLiveData<List<Double>>(listOf(0.5, 1.0, 1.5, 2.0, 2.5, 3.0, 3.5, 4.0, 4.5, 5.0))

    val scoreList = _scoreList.asLiveData()

    private val _publicDataResponseState = MutableLiveData<ViewState<PublicDataResponse>>(ViewState.initial())

    val aboutDataState = _publicDataResponseState.asLiveData()

    fun fetchPublicData() = viewModelScope.launch(Dispatchers.IO) {
        val results = publicRepo.fetchPublicData()
        when (results) {
            is Results.Successful<PublicDataResponse> -> {
                val aboutData = results.data
                if (((aboutData.shareLink.isEmpty()
                            && aboutData.aboutCoaching.isEmpty()
                            && aboutData.commonProblemList.isEmpty())
                            && aboutData.privacyPolicy.isEmpty())
                    && aboutData.contactUs.isNotEmpty()
                )
                    _publicDataResponseState.postValue(ViewState.empty())
                else
                    _publicDataResponseState.postValue(ViewState.data(aboutData))
            }
            is Results.ClientErrors -> {
                _publicDataResponseState.postValue(ViewState.error(results.e))
            }
            is Results.NetWorkError -> {
                _publicDataResponseState.postValue(ViewState.network(results.e))
            }
        }
    }

    private val _shareLink = MutableLiveData<String>()

    val shareLink = _shareLink.asLiveData()

    fun setShareLink(shareLink: String) = _shareLink.postValue(shareLink)

    private val _aboutCoaching = MutableLiveData<String>()

    val aboutCoaching = _aboutCoaching.asLiveData()

    fun setAboutCoaching(aboutCoaching: String) = _aboutCoaching.postValue(aboutCoaching)

    private val _commonProblemList = MutableLiveData<List<CommonProblem>>()

    val commonProblemList = _commonProblemList.asLiveData()

    fun setCommonProblemList(commonProblemList: List<CommonProblem>) = _commonProblemList.postValue(commonProblemList)

    private val _privacyPolicy = MutableLiveData<String>()

    val privacyPolicy = _privacyPolicy.asLiveData()

    fun setPrivacyPolicy(privacyPolicy: String) = _privacyPolicy.postValue(privacyPolicy)

    private val _contactUs = MutableLiveData<String>()

    val contactUs = _contactUs.asLiveData()

    fun setContactUs(contactUs: String) = _contactUs.postValue(contactUs)

    private val _reflectList = MutableLiveData<List<Reflect>>()

    val reflectList = _reflectList.asLiveData()

    fun setReflect(reflectList: List<Reflect>) = _reflectList.postValue(reflectList)

}