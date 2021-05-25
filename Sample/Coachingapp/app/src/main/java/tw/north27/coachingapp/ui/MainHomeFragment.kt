package tw.north27.coachingapp.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.yujie.basemodule.BaseFragment
import com.yujie.utilmodule.ViewState
import com.yujie.utilmodule.ext.observe
import org.koin.androidx.viewmodel.ext.android.viewModel
import tw.north27.coachingapp.R
import tw.north27.coachingapp.adapter.TeacherListAdapter
import tw.north27.coachingapp.databinding.FragmentMainHomeBinding
import tw.north27.coachingapp.ext.isVisible
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
        binding.srlView.autoRefresh()

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

        binding.srlView.setOnRefreshListener {
            viewModel.getLoadTeacher()
        }

        adapter.itemClickRelay.observe(viewLifecycleOwner) {
            

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