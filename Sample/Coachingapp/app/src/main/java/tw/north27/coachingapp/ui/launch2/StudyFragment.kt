package tw.north27.coachingapp.ui.launch2

import android.os.Bundle
import android.view.View
import com.yujie.utilmodule.base.BaseFragment
import tw.north27.coachingapp.R
import tw.north27.coachingapp.databinding.FragmentStudyBinding

class StudyFragment : BaseFragment<FragmentStudyBinding>(R.layout.fragment_study) {

    override val viewBind: (View) -> FragmentStudyBinding
        get() = FragmentStudyBinding::bind

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        doubleClickToExit()
    }


}