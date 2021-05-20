package tw.north27.coachingapp.ui2.fragment.main

import android.os.Bundle
import android.view.View
import com.yujie.basemodule.viewBinding
import tw.north27.coachingapp.R
import tw.north27.coachingapp.base.BaseFragment
import tw.north27.coachingapp.databinding.FragmentStudyRoomBinding

class StudyRoomFragment : BaseFragment(R.layout.fragment_study_room) {

    private val binding by viewBinding<FragmentStudyRoomBinding>(FragmentStudyRoomBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        doubleClickToExit()
    }
}