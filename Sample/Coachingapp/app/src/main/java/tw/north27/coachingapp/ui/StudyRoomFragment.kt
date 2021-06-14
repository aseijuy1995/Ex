package tw.north27.coachingapp.ui

import android.os.Bundle
import android.view.View
import com.yujie.utilmodule.base.BaseFragment
import tw.north27.coachingapp.R
import tw.north27.coachingapp.databinding.FragmentStudyRoomBinding

class StudyRoomFragment : BaseFragment<FragmentStudyRoomBinding>(R.layout.fragment_study_room) {

    override val viewBindingFactory: (View) -> FragmentStudyRoomBinding
        get() = FragmentStudyRoomBinding::bind

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        doubleClickToExit()
    }


}