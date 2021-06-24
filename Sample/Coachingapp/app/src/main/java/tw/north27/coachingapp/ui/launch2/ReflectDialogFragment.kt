package tw.north27.coachingapp.ui.launch2

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.yujie.utilmodule.base.BaseDialogFragment
import com.yujie.utilmodule.ext.clicksObserve
import tw.north27.coachingapp.R
import tw.north27.coachingapp.databinding.FragmentReflectDialogBinding

class ReflectDialogFragment : BaseDialogFragment<FragmentReflectDialogBinding>(R.layout.fragment_reflect_dialog) {

    override val viewBind: (View) -> FragmentReflectDialogBinding
        get() = FragmentReflectDialogBinding::bind


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnClose.clicksObserve(owner = viewLifecycleOwner) {
            findNavController().navigateUp()
        }
    }
}