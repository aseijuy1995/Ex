package tw.north27.coachingapp.ui

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.yujie.basemodule.BaseFragment
import com.yujie.utilmodule.ViewState
import com.yujie.utilmodule.ext.observe
import org.koin.androidx.viewmodel.ext.android.viewModel
import tw.north27.coachingapp.R
import tw.north27.coachingapp.adapter.ChapterAdapter
import tw.north27.coachingapp.adapter.GradeAdapter
import tw.north27.coachingapp.adapter.SubjectAdapter
import tw.north27.coachingapp.adapter.TeacherListAdapter
import tw.north27.coachingapp.databinding.FragmentMainHomeBinding
import tw.north27.coachingapp.ext.isVisible
import tw.north27.coachingapp.ext2.clicksObserve
import tw.north27.coachingapp.model.Grade
import tw.north27.coachingapp.viewModel.MainHomeViewModel

class MainHomeFragment : BaseFragment<FragmentMainHomeBinding>(R.layout.fragment_main_home) {

    override val viewBindingFactory: (View) -> FragmentMainHomeBinding
        get() = FragmentMainHomeBinding::bind

    private val adapter = TeacherListAdapter()

    private val viewModel by viewModel<MainHomeViewModel>()

    private val gradeAdapter = GradeAdapter()

    private val subjectAdapter = SubjectAdapter()

    private val chapterAdapter = ChapterAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        doubleClickToExit()
        val navController = (act as Launch2Activity).navController
        val appBarConfiguration = AppBarConfiguration(navController.graph, binding.drawerLayout)
        binding.tbMainHome.tbView.setupWithNavController(navController)
//        binding.nvView.setupWithNavController(navController)
        //
        binding.rvView.adapter = adapter
        binding.itemMainHomeDrawerLayout.spGrade.adapter = gradeAdapter
        binding.itemMainHomeDrawerLayout.spSubject.adapter = subjectAdapter
        binding.itemMainHomeDrawerLayout.spChapter.adapter = chapterAdapter
        //
        binding.srlView.autoRefresh()
        viewModel.getGrade()
        viewModel.getSubject()
        viewModel.getChapter()

        viewModel.teacherInfoState.observe(viewLifecycleOwner) {
            binding.itemShinner.sflView.isVisible = (it is ViewState.Load)
            binding.itemEmpty.root.isVisible = (it is ViewState.Empty)
            binding.rvView.isVisible = (it is ViewState.Data)
            binding.itemError.root.isVisible = (it is ViewState.Error)
            binding.itemNetwork.root.isVisible = (it is ViewState.Network)
            when (it) {
                is ViewState.Data -> {
                    adapter.submitList(it.data)
                    binding.srlView.finishRefresh()
                }
                is ViewState.Error, is ViewState.Network -> {
                }
            }

        }

        binding.tbMainHome.ivFilter.clicksObserve(owner = viewLifecycleOwner) {
            binding.drawerLayout.openDrawer(GravityCompat.END)
        }

        binding.srlView.setOnRefreshListener {
            viewModel.getLoadTeacher()
        }

        adapter.itemClickRelay.observe(viewLifecycleOwner) {

        }

        viewModel.gradeListState.observe(viewLifecycleOwner) {
            if (it is ViewState.Empty) {
                gradeAdapter.submitData()
            } else if (it is ViewState.Data) {
                gradeAdapter.submitData(it.data)
            }
        }
        viewModel.subjectListState.observe(viewLifecycleOwner) {
            if (it is ViewState.Empty) {
                subjectAdapter.submitData()
            } else if (it is ViewState.Data) {
                subjectAdapter.submitData(it.data)
            }
        }
        viewModel.chapterListState.observe(viewLifecycleOwner) {
            if (it is ViewState.Empty) {
                chapterAdapter.submitData()
            } else if (it is ViewState.Data) {
                chapterAdapter.submitData(it.data)
            }
        }


        binding.itemMainHomeDrawerLayout.spGrade.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.getSubject(gradeId = id)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.itemMainHomeDrawerLayout.spSubject.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val grade = binding.itemMainHomeDrawerLayout.spGrade.selectedItem as Grade
                viewModel.getChapter(gradeId = grade.id, subjectId = id)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.itemMainHomeDrawerLayout.spChapter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.itemMainHomeDrawerLayout.btnFilter.clicksObserve(owner = viewLifecycleOwner) {
            binding.drawerLayout.closeDrawer(GravityCompat.END)
        }
        binding.itemMainHomeDrawerLayout.btnCancel.clicksObserve(owner = viewLifecycleOwner) {
            binding.drawerLayout.closeDrawer(GravityCompat.END)
        }
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