package tw.north27.coachingapp.ui.launch2

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.yujie.utilmodule.base.BaseDialogFragment
import com.yujie.utilmodule.ext.clicksObserve
import tw.north27.coachingapp.R
import tw.north27.coachingapp.databinding.FragmentContactUsDialogBinding

class ContactUsDialogFragment : BaseDialogFragment<FragmentContactUsDialogBinding>(R.layout.fragment_contact_us_dialog) {

    override val viewBind: (View) -> FragmentContactUsDialogBinding
        get() = FragmentContactUsDialogBinding::bind

    private val launch2Act: Launch2Activity
        get() = act as Launch2Activity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvContent.text = launch2Act.publicVM.contactUs.value

        binding.btnClose.clicksObserve(owner = viewLifecycleOwner) {
            findNavController().navigateUp()
        }
    }
}