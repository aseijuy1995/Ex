package tw.north27.coachingapp.ui2.fragment.main

import android.os.Bundle
import android.view.View
import com.yujie.basemodule.BaseFragment
import tw.north27.coachingapp.R
import tw.north27.coachingapp.databinding.FragmentMainHomeBinding

class MainHomeFragment : BaseFragment<FragmentMainHomeBinding>(R.layout.fragment_main_home) {

    override val viewBindingFactory: (View) -> FragmentMainHomeBinding
        get() = FragmentMainHomeBinding::bind

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        doubleClickToExit()
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