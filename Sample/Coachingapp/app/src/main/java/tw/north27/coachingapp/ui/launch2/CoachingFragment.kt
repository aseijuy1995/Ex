package tw.north27.coachingapp.ui.launch2

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.yujie.utilmodule.base.BaseFragment
import com.yujie.utilmodule.ext.clicksObserve
import com.yujie.utilmodule.ext.observe
import com.yujie.utilmodule.ext.visible
import com.yujie.utilmodule.util.ViewState
import org.koin.androidx.viewmodel.ext.android.viewModel
import tw.north27.coachingapp.R
import tw.north27.coachingapp.adapter.*
import tw.north27.coachingapp.databinding.FragmentCoachingBinding
import tw.north27.coachingapp.model.response.EducationLevel
import tw.north27.coachingapp.model.response.Grade
import tw.north27.coachingapp.model.response.Subject
import tw.north27.coachingapp.model.response.Units
import tw.north27.coachingapp.viewModel.CoachingViewModel

class CoachingFragment : BaseFragment<FragmentCoachingBinding>(R.layout.fragment_coaching) {

    override val viewBind: (View) -> FragmentCoachingBinding
        get() = FragmentCoachingBinding::bind

    private val launch2Act: Launch2Activity
        get() = act as Launch2Activity

    private lateinit var adapter: TeacherListAdapter

    private val viewModel by viewModel<CoachingViewModel>()

    private val educationAdapter = EducationAdapter()

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
            rvCoaching.adapter = adapter
            itemDrawerLayoutCoaching.apply {
                spEducation.adapter = educationAdapter
                spGrade.adapter = gradeAdapter
                spSubject.adapter = subjectAdapter
                spUnit.adapter = unitAdapter
            }
        }

        viewModel.teacherListState.observe(viewLifecycleOwner) {
            binding.itemCoachingLoad.root.visible = (it is ViewState.Load)
            binding.itemEmpty.root.isVisible = (it is ViewState.Empty)
            binding.rvCoaching.isVisible = (it is ViewState.Data)
            binding.itemError.root.isVisible = (it is ViewState.Error)
            binding.itemNetwork.root.isVisible = (it is ViewState.Network)
            if (it !is ViewState.Initial && it !is ViewState.Load)
                binding.srlView.finishRefresh()
            when (it) {
                is ViewState.Data -> {
                    adapter.submitList(it.data)
                }
            }
        }

        launch2Act.publicVM.educationLevelList.observe(viewLifecycleOwner) {
            educationAdapter.submitData(mutableListOf(launch2Act.publicVM.defaultEducation).apply { addAll(it) })
            binding.itemDrawerLayoutCoaching.spEducation.setSelection(0)
        }

        launch2Act.publicVM.gradeList.observe(viewLifecycleOwner) {
            gradeAdapter.submitData(mutableListOf(launch2Act.publicVM.defaultGradle).apply { addAll(it) })
            binding.itemDrawerLayoutCoaching.spGrade.setSelection(0)
        }

        launch2Act.publicVM.subjectList.observe(viewLifecycleOwner) {
            subjectAdapter.submitData(mutableListOf(launch2Act.publicVM.defaultSubject).apply { addAll(it) })
            binding.itemDrawerLayoutCoaching.spSubject.setSelection(0)
        }

        launch2Act.publicVM.unitList.observe(viewLifecycleOwner) {
            unitAdapter.submitData(mutableListOf(launch2Act.publicVM.defaultUnit).apply { addAll(it) })
            binding.itemDrawerLayoutCoaching.spUnit.setSelection(0)
        }

        binding.itemToolbarNormal.ivFilter.clicksObserve(owner = viewLifecycleOwner) {
            binding.drawerLayout.openDrawer(GravityCompat.END)
        }

        binding.srlView.setOnRefreshListener {
            val educationId = (binding.itemDrawerLayoutCoaching.spEducation.selectedItem as EducationLevel).id
            val gradeId = (binding.itemDrawerLayoutCoaching.spGrade.selectedItem as Grade).id
            val subjectId = (binding.itemDrawerLayoutCoaching.spSubject.selectedItem as Subject).id
            val unitId = (binding.itemDrawerLayoutCoaching.spUnit.selectedItem as Units).id
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
            findNavController().navigate(CoachingFragmentDirections.actionFragmentCoachingToFragmentTeacherDialog(it.second))
        }

        binding.itemDrawerLayoutCoaching.spEducation.onItemSelectedEvenIfUnchangedListener = object : AdapterView.OnItemSelectedListener {
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

        binding.itemDrawerLayoutCoaching.spGrade.onItemSelectedEvenIfUnchangedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.itemDrawerLayoutCoaching.spSubject.onItemSelectedEvenIfUnchangedListener = object : AdapterView.OnItemSelectedListener {
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

        binding.itemDrawerLayoutCoaching.spUnit.onItemSelectedEvenIfUnchangedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.itemDrawerLayoutCoaching.btnFilter.clicksObserve(owner = viewLifecycleOwner) {
            binding.srlView.autoRefresh()
            binding.drawerLayout.closeDrawer(GravityCompat.END)
        }

        binding.itemDrawerLayoutCoaching.btnClear.clicksObserve(owner = viewLifecycleOwner) {
            setDfSelection()
        }

        binding.itemDrawerLayoutCoaching.btnCancel.clicksObserve(owner = viewLifecycleOwner) {
            binding.drawerLayout.closeDrawer(GravityCompat.END)
        }

        setDfSelection()
        binding.srlView.autoRefresh()
    }

    //確認初始數據
    private fun setDfSelection() {
        educationAdapter.submitData(mutableListOf(launch2Act.publicVM.defaultEducation).apply { launch2Act.publicVM.educationLevelList.value?.let { addAll(it) } })
        gradeAdapter.submitData(mutableListOf(launch2Act.publicVM.defaultGradle).apply { launch2Act.publicVM.gradeList.value?.let { addAll(it) } })
        subjectAdapter.submitData(mutableListOf(launch2Act.publicVM.defaultSubject).apply { launch2Act.publicVM.subjectList.value?.let { addAll(it) } })
        unitAdapter.submitData(mutableListOf(launch2Act.publicVM.defaultUnit).apply { launch2Act.publicVM.unitList.value?.let { addAll(it) } })
        binding.itemDrawerLayoutCoaching.spEducation.setSelection(0)
        binding.itemDrawerLayoutCoaching.spGrade.setSelection(0)
        binding.itemDrawerLayoutCoaching.spSubject.setSelection(0)
        binding.itemDrawerLayoutCoaching.spUnit.setSelection(0)
    }


}