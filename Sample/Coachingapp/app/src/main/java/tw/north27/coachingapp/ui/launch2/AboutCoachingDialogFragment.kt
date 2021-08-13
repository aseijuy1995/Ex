package tw.north27.coachingapp.ui.launch2

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.yujie.core_lib.base.BaseDialogFragment
import com.yujie.core_lib.ext.clicksObserve
import tw.north27.coachingapp.R
import tw.north27.coachingapp.databinding.FragmentAboutCoachingDialogBinding
import tw.north27.coachingapp.ui.launch2.basic.Launch2Activity

class AboutCoachingDialogFragment : BaseDialogFragment<FragmentAboutCoachingDialogBinding>(R.layout.fragment_about_coaching_dialog) {

    override val bind: (View) -> FragmentAboutCoachingDialogBinding
        get() = FragmentAboutCoachingDialogBinding::bind

    private val launch2Act: Launch2Activity
        get() = act as Launch2Activity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvContent.text = launch2Act.publicVM.aboutCoaching.value

        binding.btnClose.clicksObserve(owner = viewLifecycleOwner) {
            findNavController().navigateUp()
        }
    }
}