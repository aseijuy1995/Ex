package tw.north27.coachingapp.ui.launch2

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.yujie.core_lib.base.BaseFragment
import com.yujie.core_lib.ext.clicksObserve
import com.yujie.core_lib.ext.observe
import com.yujie.core_lib.ext.visible
import com.yujie.core_lib.util.ViewState
import org.koin.androidx.viewmodel.ext.android.viewModel
import tw.north27.coachingapp.NavGraphLaunch2Directions
import tw.north27.coachingapp.R
import tw.north27.coachingapp.adapter.*
import tw.north27.coachingapp.databinding.FragmentCoachingBinding
import tw.north27.coachingapp.model.From
import tw.north27.coachingapp.model.response.EducationLevel
import tw.north27.coachingapp.model.response.Grade
import tw.north27.coachingapp.model.response.Subject
import tw.north27.coachingapp.model.response.UnitType
import tw.north27.coachingapp.viewModel.CoachingViewModel

class CoachingFragment : BaseFragment<FragmentCoachingBinding>(R.layout.fragment_coaching) {

    override val viewBind: (View) -> FragmentCoachingBinding
        get() = FragmentCoachingBinding::bind

    private val launch2Act: Launch2Activity
        get() = act as Launch2Activity

    private lateinit var adapter: TeacherListAdapter

    private val viewModel by viewModel<CoachingViewModel>()

    private val educationLevelAdapter = EducationLevelAdapter()

    private val gradeAdapter = GradeAdapter()

    private val subjectAdapter = SubjectAdapter()

    private val unitAdapter = UnitAdapter()

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
                rsUnitType.adapter = unitAdapter
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

        launch2Act.publicVM.educationLevelList.observe(viewLifecycleOwner) {
            educationLevelAdapter.submitData(mutableListOf(launch2Act.publicVM.defaultEducation).apply { addAll(it) })
            binding.itemDrawerLayoutCoaching.rsEducationLevel.setSelection(0)
        }

        launch2Act.publicVM.gradeList.observe(viewLifecycleOwner) {
            gradeAdapter.submitData(mutableListOf(launch2Act.publicVM.defaultGradle).apply { addAll(it) })
            binding.itemDrawerLayoutCoaching.rsGrade.setSelection(0)
        }

        launch2Act.publicVM.subjectList.observe(viewLifecycleOwner) {
            subjectAdapter.submitData(mutableListOf(launch2Act.publicVM.defaultSubject).apply { addAll(it) })
            binding.itemDrawerLayoutCoaching.rsSubject.setSelection(0)
        }

        launch2Act.publicVM.unitList.observe(viewLifecycleOwner) {
            unitAdapter.submitData(mutableListOf(launch2Act.publicVM.defaultUnit).apply { addAll(it) })
            binding.itemDrawerLayoutCoaching.rsUnitType.setSelection(0)
        }

        binding.itemToolbarNormal.ivFilter.clicksObserve(owner = viewLifecycleOwner) {
            binding.dlLayout.openDrawer(GravityCompat.END)
        }

        binding.srlLayout.setOnRefreshListener {
            val educationId = (binding.itemDrawerLayoutCoaching.rsEducationLevel.selectedItem as EducationLevel).id
            val gradeId = (binding.itemDrawerLayoutCoaching.rsGrade.selectedItem as Grade).id
            val subjectId = (binding.itemDrawerLayoutCoaching.rsSubject.selectedItem as Subject).id
            val unitId = (binding.itemDrawerLayoutCoaching.rsUnitType.selectedItem as UnitType).id
            viewModel.fetchTeacherList(
                educationId = if (educationId != -1L) educationId else null,
                gradeId = if (gradeId != -1L) gradeId else null,
                subjectId = if (subjectId != -1L) subjectId else null,
                unitId = if (unitId != -1L) unitId else null,
                index = 0,
                num = 20
            )
        }

        adapter.itemClickRelay.observe(viewLifecycleOwner) {
            val clientInfo = it.second
            findNavController().navigate(NavGraphLaunch2Directions.actionToFragmentTeacherDialog(From.Specify, clientInfo, null))
        }

        binding.itemDrawerLayoutCoaching.rsEducationLevel.onItemSelectedEvenIfUnchangedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                gradeAdapter.submitData(
                    mutableListOf(launch2Act.publicVM.defaultGradle).apply {
                        addAll(
                            if (id == -1L)
                                launch2Act.publicVM.gradeList.value ?: emptyList()
                            else
                                launch2Act.publicVM.gradeList.value?.filter { it.educationLevelId == id } ?: emptyList()
                        )
                    }
                )
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.itemDrawerLayoutCoaching.rsGrade.onItemSelectedEvenIfUnchangedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.itemDrawerLayoutCoaching.rsSubject.onItemSelectedEvenIfUnchangedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                unitAdapter.submitData(
                    mutableListOf(launch2Act.publicVM.defaultUnit).apply {
                        addAll(
                            if (id == -1L)
                                launch2Act.publicVM.unitList.value ?: emptyList()
                            else
                                launch2Act.publicVM.unitList.value?.filter { it.subjectId == id } ?: emptyList()
                        )
                    }
                )
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.itemDrawerLayoutCoaching.rsUnitType.onItemSelectedEvenIfUnchangedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.itemDrawerLayoutCoaching.btnFilter.clicksObserve(owner = viewLifecycleOwner) {
            binding.srlLayout.autoRefresh()
            binding.dlLayout.closeDrawer(GravityCompat.END)
        }

        binding.itemDrawerLayoutCoaching.btnClear.clicksObserve(owner = viewLifecycleOwner) {
            setDfSelection()
        }

        binding.itemDrawerLayoutCoaching.btnCancel.clicksObserve(owner = viewLifecycleOwner) {
            binding.dlLayout.closeDrawer(GravityCompat.END)
        }

        setDfSelection()
        binding.srlLayout.autoRefresh()
    }

    //確認初始數據
    private fun setDfSelection() {
        educationLevelAdapter.submitData(mutableListOf(launch2Act.publicVM.defaultEducation).apply { launch2Act.publicVM.educationLevelList.value?.let { addAll(it) } })
        gradeAdapter.submitData(mutableListOf(launch2Act.publicVM.defaultGradle).apply { launch2Act.publicVM.gradeList.value?.let { addAll(it) } })
        subjectAdapter.submitData(mutableListOf(launch2Act.publicVM.defaultSubject).apply { launch2Act.publicVM.subjectList.value?.let { addAll(it) } })
        unitAdapter.submitData(mutableListOf(launch2Act.publicVM.defaultUnit).apply { launch2Act.publicVM.unitList.value?.let { addAll(it) } })
        binding.itemDrawerLayoutCoaching.rsEducationLevel.setSelection(0)
        binding.itemDrawerLayoutCoaching.rsGrade.setSelection(0)
        binding.itemDrawerLayoutCoaching.rsSubject.setSelection(0)
        binding.itemDrawerLayoutCoaching.rsUnitType.setSelection(0)
    }


}