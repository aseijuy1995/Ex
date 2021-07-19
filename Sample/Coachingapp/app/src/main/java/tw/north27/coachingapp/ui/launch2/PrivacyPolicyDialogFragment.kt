package tw.north27.coachingapp.ui.launch2

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.yujie.core_lib.base.BaseDialogFragment
import com.yujie.core_lib.ext.clicksObserve
import tw.north27.coachingapp.R
import tw.north27.coachingapp.databinding.FragmentPrivacyPolicyDialogBinding
import tw.north27.coachingapp.ui.launch2.basic.Launch2Activity

class PrivacyPolicyDialogFragment : BaseDialogFragment<FragmentPrivacyPolicyDialogBinding>(R.layout.fragment_privacy_policy_dialog) {

    override val viewBind: (View) -> FragmentPrivacyPolicyDialogBinding
        get() = FragmentPrivacyPolicyDialogBinding::bind

    private val launch2Act: Launch2Activity
        get() = act as Launch2Activity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvContent.text = launch2Act.publicVM.privacyPolicy.value

        binding.btnClose.clicksObserve(owner = viewLifecycleOwner) {
            findNavController().navigateUp()
        }
    }
}