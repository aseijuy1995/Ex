package tw.north27.coachingapp.ui.launch2.basic

import android.os.Bundle
import android.view.View
import com.yujie.core_lib.base.BaseFragment
import tw.north27.coachingapp.R
import tw.north27.coachingapp.databinding.FragmentStudyBinding

class StudyFragment : BaseFragment<FragmentStudyBinding>(R.layout.fragment_study) {

    override val viewBind: (View) -> FragmentStudyBinding
        get() = FragmentStudyBinding::bind

    private val launch2Act: Launch2Activity
        get() = act as Launch2Activity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launch2Act.doubleClickToExit()
    }


}