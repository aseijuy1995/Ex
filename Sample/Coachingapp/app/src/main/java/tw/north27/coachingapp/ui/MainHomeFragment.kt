package tw.north27.coachingapp.ui//package tw.north27.coachingapp.ui

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.yujie.utilmodule.base.BaseFragment
import com.yujie.utilmodule.ext.clicksObserve
import com.yujie.utilmodule.ext.observe
import com.yujie.utilmodule.util.ViewState
import com.yujie.utilmodule.util.logI
import org.koin.androidx.viewmodel.ext.android.viewModel
import tw.north27.coachingapp.R
import tw.north27.coachingapp.adapter.*
import tw.north27.coachingapp.databinding.FragmentMainHomeBinding
import tw.north27.coachingapp.model.Education
import tw.north27.coachingapp.model.Grade
import tw.north27.coachingapp.model.Subject
import tw.north27.coachingapp.model.Unit
import tw.north27.coachingapp.ui2.Launch2Activity
import tw.north27.coachingapp.viewModel.MainHomeViewModel

class MainHomeFragment : BaseFragment<FragmentMainHomeBinding>(R.layout.fragment_main_home) {

    override val viewBindingFactory: (View) -> FragmentMainHomeBinding
        get() = FragmentMainHomeBinding::bind

    private val adapter = TeacherListAdapter()

    private val viewModel by viewModel<MainHomeViewModel>()

    private val educationAdapter = EducationAdapter()

    private val gradeAdapter = GradeAdapter()

    private val subjectAdapter = SubjectAdapter()

    private val chapterAdapter = UnitAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        doubleClickToExit()
        val navController = (act as Launch2Activity).navController
        val appBarConfiguration = AppBarConfiguration(navController.graph, binding.drawerLayout)
        binding.tbMainHome.tbView.setupWithNavController(navController)
//        binding.nvView.setupWithNavController(navController)
        //
        binding.rvView.adapter = adapter
        binding.itemDrawerLayoutMainHome.spEducation.adapter = educationAdapter
        binding.itemDrawerLayoutMainHome.spGrade.adapter = gradeAdapter
        binding.itemDrawerLayoutMainHome.spSubject.adapter = subjectAdapter
        binding.itemDrawerLayoutMainHome.spUnit.adapter = chapterAdapter

        getDefaultSelection()
        binding.srlView.autoRefresh()

        viewModel.teacherListState.observe(viewLifecycleOwner) {
            binding.itemShinner.sflView.isVisible = (it is ViewState.Load)
            binding.itemEmpty.root.isVisible = (it is ViewState.Empty)
            binding.rvView.isVisible = (it is ViewState.Data)
            binding.itemError.root.isVisible = (it is ViewState.Error)
            binding.itemNetwork.root.isVisible = (it is ViewState.Network)
            if (it !is ViewState.Initial && it !is ViewState.Load)
                binding.srlView.finishRefresh()
            when (it) {
                is ViewState.Data -> {
                    adapter.submitList(it.data)
                }
                //FIXME　整合處理各頁面錯誤
                is ViewState.Error, is ViewState.Network -> {
                }
            }

        }

        binding.tbMainHome.ivFilter.clicksObserve(owner = viewLifecycleOwner) {
            binding.drawerLayout.openDrawer(GravityCompat.END)
        }

        binding.srlView.setOnRefreshListener {
            getTeacherList()
        }

        adapter.itemClickRelay.observe(viewLifecycleOwner) {

        }

        viewModel.educationListState.observe(viewLifecycleOwner) {
            if (it is ViewState.Empty)
                educationAdapter.submitData(listOf(viewModel.defaultEducation))
            else if (it is ViewState.Data) {
                val educationList = it.data.toMutableList()
                educationList.add(0, viewModel.defaultEducation)
                educationAdapter.submitData(educationList)
            }
            if (it is ViewState.Empty || it is ViewState.Data)
                binding.itemDrawerLayoutMainHome.spEducation.setSelection(0)
        }
        viewModel.gradeListState.observe(viewLifecycleOwner) {
            if (it is ViewState.Empty)
                gradeAdapter.submitData(listOf(viewModel.defaultGradle))
            else if (it is ViewState.Data) {
                val gradleList = it.data.toMutableList()
                gradleList.add(0, viewModel.defaultGradle)
                gradeAdapter.submitData(gradleList)
            }
            if (it is ViewState.Empty || it is ViewState.Data)
                binding.itemDrawerLayoutMainHome.spGrade.setSelection(0)
        }
        viewModel.subjectListState.observe(viewLifecycleOwner) {
            if (it is ViewState.Empty)
                subjectAdapter.submitData(listOf(viewModel.defaultSubject))
            else if (it is ViewState.Data) {
                val subjectList = it.data.toMutableList()
                subjectList.add(0, viewModel.defaultSubject)
                subjectAdapter.submitData(subjectList)
            }
            if (it is ViewState.Empty || it is ViewState.Data)
                binding.itemDrawerLayoutMainHome.spSubject.setSelection(0)
        }
        viewModel.unitListState.observe(viewLifecycleOwner) {
            if (it is ViewState.Empty)
                chapterAdapter.submitData(listOf(viewModel.defaultUnit))
            else if (it is ViewState.Data) {
                val unitList = it.data.toMutableList()
                unitList.add(0, viewModel.defaultUnit)
                chapterAdapter.submitData(unitList)
            }
            if (it is ViewState.Empty || it is ViewState.Data)
                binding.itemDrawerLayoutMainHome.spUnit.setSelection(0)
        }

        binding.itemDrawerLayoutMainHome.spEducation.onItemSelectedEvenIfUnchangedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.getGradeList(educationId = id)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.itemDrawerLayoutMainHome.spGrade.onItemSelectedEvenIfUnchangedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val education = binding.itemDrawerLayoutMainHome.spEducation.selectedItem as Education
                viewModel.getSubjectList(educationId = education.id, gradeId = id)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.itemDrawerLayoutMainHome.spSubject.onItemSelectedEvenIfUnchangedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val education = binding.itemDrawerLayoutMainHome.spEducation.selectedItem as Education
                val grade = binding.itemDrawerLayoutMainHome.spGrade.selectedItem as Grade
                viewModel.getUnitList(educationId = education.id, gradeId = grade.id, subjectId = id)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.itemDrawerLayoutMainHome.spUnit.onItemSelectedEvenIfUnchangedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.itemDrawerLayoutMainHome.btnFilter.clicksObserve(owner = viewLifecycleOwner) {
            binding.srlView.autoRefresh()
            binding.drawerLayout.closeDrawer(GravityCompat.END)
        }

        binding.itemDrawerLayoutMainHome.btnClear.clicksObserve(owner = viewLifecycleOwner) {
            getDefaultSelection()
        }

        binding.itemDrawerLayoutMainHome.btnCancel.clicksObserve(owner = viewLifecycleOwner) {
            binding.drawerLayout.closeDrawer(GravityCompat.END)
        }
    }

    private fun getTeacherList() {
        val education = binding.itemDrawerLayoutMainHome.spEducation.selectedItem as Education
        val grade = binding.itemDrawerLayoutMainHome.spGrade.selectedItem as Grade
        val subject = binding.itemDrawerLayoutMainHome.spSubject.selectedItem as Subject
        val chapter = binding.itemDrawerLayoutMainHome.spUnit.selectedItem as Unit
        logI("grade.id = ${grade.id}, subject.id = ${subject.id}, chapter.id = ${chapter.id}")
        viewModel.getTeacherList(
            educationId = education.id,
            gradeId = grade.id,
            subjectId = subject.id,
            unitId = chapter.id
        )
    }

    private fun getDefaultSelection() {
        viewModel.getEducationList()
        viewModel.getGradeList()
        viewModel.getSubjectList()
        viewModel.getUnitList()
    }

//    var count = 0
//
//    fun doubleClickToExit() {
//        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
//            count++
//            if (count == 1)
//                Toast.makeText(cxt, "再點選一次以退出!", Toast.LENGTH_SHORT).show()
//            else if (count == 2)
//                act.finishAffinity()
//
//            lifecycleScope.launch(Dispatchers.IO) {
//                delay(1000)
//                count = 0
//            }
//        }
//    }

}