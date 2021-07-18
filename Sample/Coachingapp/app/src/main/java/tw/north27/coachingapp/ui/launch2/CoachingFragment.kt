package tw.north27.coachingapp.ui.launch2

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.yujie.core_lib.base.BaseFragment
import com.yujie.core_lib.ext.clicksObserve
import com.yujie.core_lib.ext.observe
import com.yujie.core_lib.ext.visible
import com.yujie.core_lib.util.ViewState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import tw.north27.coachingapp.NavGraphLaunch2Directions
import tw.north27.coachingapp.R
import tw.north27.coachingapp.adapter.*
import tw.north27.coachingapp.databinding.FragmentCoachingBinding
import tw.north27.coachingapp.model.ClientInfo
import tw.north27.coachingapp.model.transfer.SourceFrom
import tw.north27.coachingapp.model.response.*
import tw.north27.coachingapp.ui.launch2.ask.EducationSelectorDialogFragment
import tw.north27.coachingapp.viewModel.CoachingViewModel
import tw.north27.coachingapp.viewModel.PublicViewModel

class CoachingFragment : BaseFragment<FragmentCoachingBinding>(R.layout.fragment_coaching) {

    override val viewBind: (View) -> FragmentCoachingBinding
        get() = FragmentCoachingBinding::bind

    private val publicVM by sharedViewModel<PublicViewModel>()

    private val viewModel by viewModel<CoachingViewModel>()

    private val launch2Act: Launch2Activity
        get() = act as Launch2Activity

    private lateinit var adapter: TeacherListAdapter

    private val educationLevelAdapter = EducationLevelAdapter()

    private val gradeAdapter = GradeAdapter()

    private val subjectAdapter = SubjectAdapter()

    private val unitTypeAdapter = UnitTypeAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launch2Act.doubleClickToExit()
        adapter = TeacherListAdapter(launch2Act)
        binding.apply {
            itemToolbarNormal.apply {
                ivBack.isVisible = false
                ivFilter.isVisible = true
                tvTitle.text = getString(R.string.coaching)
            }
            rvView.adapter = adapter
            itemDrawerLayoutCoaching.apply {
                rsEducationLevel.adapter = educationLevelAdapter
                rsGrade.adapter = gradeAdapter
                rsSubject.adapter = subjectAdapter
                rsUnitType.adapter = unitTypeAdapter
            }
        }

        publicVM.educationState.observe(viewLifecycleOwner) {
            when (it) {
                is ViewState.Data -> {
                    val education = it.data
                    setDfSelection(education)
                    binding.srlLayout.autoRefresh()
                }
            }
        }

        viewModel.teacherListState.observe(viewLifecycleOwner) {
            binding.itemCoachingLoad.root.visible = (it is ViewState.Load)
            binding.itemEmpty.root.isVisible = (it is ViewState.Empty)
            binding.rvView.isVisible = (it is ViewState.Data)
            binding.itemError.root.isVisible = (it is ViewState.Error)
            binding.itemNetwork.root.isVisible = (it is ViewState.Network)
            if (it !is ViewState.Initial && it !is ViewState.Load)
                binding.srlLayout.finishRefresh()
            when (it) {
                is ViewState.Data -> {
                    adapter.submitList(it.data)
                }
            }
        }

        binding.srlLayout.setOnRefreshListener {
            val educationLevelId = (binding.itemDrawerLayoutCoaching.rsEducationLevel.selectedItem as EducationLevel).id
            val gradeId = (binding.itemDrawerLayoutCoaching.rsGrade.selectedItem as Grade).id
            val subjectId = (binding.itemDrawerLayoutCoaching.rsSubject.selectedItem as Subject).id
            val unitTypeId = (binding.itemDrawerLayoutCoaching.rsUnitType.selectedItem as UnitType).id
            viewModel.fetchTeacherList(
                educationLevelId = educationLevelId,
                gradeId = gradeId,
                subjectId = subjectId,
                unitTypeId = unitTypeId,
                index = 0,
                num = 100
            )
        }

        adapter.itemClickRelay.observe(viewLifecycleOwner) {
            val clientInfo = it.second
            findNavController().navigate(
                NavGraphLaunch2Directions.actionToFragmentTeacherDetailDialog(
                    sourceFrom = SourceFrom.Specify,
                    clientInfo = clientInfo,
                    unitType = null
                )
            )
        }

        //老師詳細頁返回
        setFragmentResultListener(TeacherDetailDialogFragment.REQUEST_KEY_TEACHER) { key, bundle ->
            lifecycleScope.launch {
                delay(500)
                val clientInfo: ClientInfo = bundle.getParcelable<ClientInfo>(TeacherDetailDialogFragment.KEY_TEACHER_CLIENT)!!
                findNavController().navigate(
                    NavGraphLaunch2Directions.actionToFragmentEducationSelectorDialog(
                        sourceFrom = SourceFrom.Specify,
                        clientInfo = clientInfo
                    )
                )
            }
        }

        //篩選器頁返回
        setFragmentResultListener(EducationSelectorDialogFragment.REQUEST_KEY_SELECTOR) { key, bundle ->
            lifecycleScope.launch {
                delay(500)
                val clientInfo: ClientInfo = bundle.getParcelable<ClientInfo>(EducationSelectorDialogFragment.KEY_SELECTOR_CLIENT)!!
                val unitType: UnitType = bundle.getParcelable<UnitType>(EducationSelectorDialogFragment.KEY_SELECTOR_UNITTYPE)!!
                val msg: String = bundle.getString(EducationSelectorDialogFragment.KEY_SELECTOR_MSG)!!
                findNavController().navigate(
                    NavGraphLaunch2Directions.actionToFragmentSetupAskRoomDialog(
                        clientInfo = clientInfo,
                        unitType = unitType,
                        msg = msg
                    )
                )
            }
        }

        binding.itemDrawerLayoutCoaching.rsEducationLevel.onItemSelectedEvenIfUnchangedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val education = (publicVM.educationState.value as ViewState.Data).data
                gradeAdapter.submitData(
                    mutableListOf(publicVM.defaultGradle).apply {
                        addAll(
                            if (id == -1L)
                                education.gradeList
                            else
                                education.gradeList.filter { it.educationLevelId == id }
                        )
                    }
                )
                binding.itemDrawerLayoutCoaching.rsGrade.setSelection(0)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.itemDrawerLayoutCoaching.rsGrade.onItemSelectedEvenIfUnchangedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val education = (publicVM.educationState.value as ViewState.Data).data
                val educationLevelId = binding.itemDrawerLayoutCoaching.rsEducationLevel.selectedItemId
                subjectAdapter.submitData(
                    mutableListOf(publicVM.defaultSubject).apply {
                        addAll(
                            education.subjectList.filter {
                                if (educationLevelId == -1L && id == -1L)
                                    true
                                else if (educationLevelId == -1L)
                                    it.gradeIdList.any { it == id }
                                else if (id == -1L)
                                    it.educationLevelIdList.any { it == educationLevelId }
                                else
                                    it.educationLevelIdList.any { it == educationLevelId } && it.gradeIdList.any { it == id }
                            }
                        )
                    }
                )
                binding.itemDrawerLayoutCoaching.rsSubject.setSelection(0)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.itemDrawerLayoutCoaching.rsSubject.onItemSelectedEvenIfUnchangedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val education = (publicVM.educationState.value as ViewState.Data).data
                val educationLevelId = binding.itemDrawerLayoutCoaching.rsEducationLevel.selectedItemId
                val gradeId = binding.itemDrawerLayoutCoaching.rsGrade.selectedItemId
                unitTypeAdapter.submitData(
                    mutableListOf(publicVM.defaultUnit).apply {
                        addAll(
                            education.unitTypeList.filter {
                                if (educationLevelId == -1L && gradeId == -1L && id == -1L)
                                    true
                                else if (educationLevelId == -1L && gradeId == -1L)
                                    it.subjectId == id
                                else if (educationLevelId == -1L && id == -1L)
                                    it.gradeId == gradeId
                                else if (gradeId == -1L && id == -1L)
                                    it.educationLevelId == educationLevelId
                                else if (educationLevelId == -1L)
                                    it.gradeId == gradeId && it.subjectId == id
                                else if (gradeId == -1L)
                                    it.educationLevelId == educationLevelId && it.subjectId == id
                                else if (id == -1L)
                                    it.educationLevelId == educationLevelId && it.gradeId == gradeId
                                else
                                    it.educationLevelId == educationLevelId && it.gradeId == gradeId && it.subjectId == id
                            }
                        )
                    }
                )
                binding.itemDrawerLayoutCoaching.rsUnitType.setSelection(0)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.itemToolbarNormal.ivFilter.clicksObserve(owner = viewLifecycleOwner) {
            binding.dlLayout.openDrawer(GravityCompat.END)
        }

        binding.itemDrawerLayoutCoaching.btnFilter.clicksObserve(owner = viewLifecycleOwner) {
            binding.srlLayout.autoRefresh()
            binding.dlLayout.closeDrawer(GravityCompat.END)
        }

        binding.itemDrawerLayoutCoaching.btnClear.clicksObserve(owner = viewLifecycleOwner) {
            val education = (publicVM.educationState.value as ViewState.Data).data
            setDfSelection(education)
        }

        binding.itemDrawerLayoutCoaching.btnCancel.clicksObserve(owner = viewLifecycleOwner) {
            binding.dlLayout.closeDrawer(GravityCompat.END)
        }

    }

    //確認初始數據
    private fun setDfSelection(education: Education) {
        educationLevelAdapter.submitData(mutableListOf(publicVM.defaultEducation).apply { addAll(education.educationLevelList) })
        gradeAdapter.submitData(mutableListOf(publicVM.defaultGradle).apply { addAll(education.gradeList) })
        subjectAdapter.submitData(mutableListOf(publicVM.defaultSubject).apply { addAll(education.subjectList) })
        unitTypeAdapter.submitData(mutableListOf(publicVM.defaultUnit).apply { addAll(education.unitTypeList) })
        binding.itemDrawerLayoutCoaching.apply {
            rsEducationLevel.setSelection(0)
            rsGrade.setSelection(0)
            rsSubject.setSelection(0)
            rsUnitType.setSelection(0)
        }
    }

}