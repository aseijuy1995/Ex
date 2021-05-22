package tw.north27.coachingapp.ui

import android.os.Bundle
import android.view.View
import com.yujie.basemodule.BaseFragment
import com.yujie.utilmodule.ViewState
import org.koin.androidx.viewmodel.ext.android.viewModel
import tw.north27.coachingapp.R
import tw.north27.coachingapp.adapter.TeacherListAdapter
import tw.north27.coachingapp.databinding.FragmentMainHomeBinding
import tw.north27.coachingapp.viewModel.MainHomeViewModel

class MainHomeFragment : BaseFragment<FragmentMainHomeBinding>(R.layout.fragment_main_home) {

    override val viewBindingFactory: (View) -> FragmentMainHomeBinding
        get() = FragmentMainHomeBinding::bind

    private val adapter = TeacherListAdapter()

    private val viewModel by viewModel<MainHomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        doubleClickToExit()

        binding.rvView.adapter = adapter

        viewModel.getLoadTeacher()

        viewModel.teacherInfoState.observe(viewLifecycleOwner) {
            when (it) {
                is ViewState.Load -> {

                }
                is ViewState.Empty -> {

                }
                is ViewState.Data -> {
                    adapter.submitList(it.data)
                }
                is ViewState.Error, is ViewState.Network -> {
                }
            }

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