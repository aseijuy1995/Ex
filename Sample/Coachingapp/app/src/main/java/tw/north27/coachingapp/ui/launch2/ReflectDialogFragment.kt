package tw.north27.coachingapp.ui.launch2

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.yujie.core_lib.base.BaseDialogFragment
import com.yujie.core_lib.ext.clicksObserve
import com.yujie.core_lib.util.ViewState
import org.koin.androidx.viewmodel.ext.android.viewModel
import tw.north27.coachingapp.R
import tw.north27.coachingapp.adapter.info.ReflectAdapter
import tw.north27.coachingapp.databinding.FragmentReflectDialogBinding
import tw.north27.coachingapp.ui.launch2.share.LoadingDialogFragment
import tw.north27.coachingapp.ui.launch2.basic.Launch2Activity
import tw.north27.coachingapp.viewModel.PersonalViewModel

class ReflectDialogFragment : BaseDialogFragment<FragmentReflectDialogBinding>(R.layout.fragment_reflect_dialog) {

    override val bind: (View) -> FragmentReflectDialogBinding
        get() = FragmentReflectDialogBinding::bind

    private val viewModel by viewModel<PersonalViewModel>()

    private val launch2Act: Launch2Activity
        get() = act as Launch2Activity

    private val reflectAdapter = ReflectAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.spReflect.adapter = reflectAdapter

        launch2Act.publicVM.reflectList.observe(viewLifecycleOwner) {
            reflectAdapter.submitData(it)
        }

        binding.btnClose.clicksObserve(owner = viewLifecycleOwner) {
            findNavController().navigateUp()
        }

        binding.btnSend.clicksObserve(owner = viewLifecycleOwner) {
            if (binding.etContent.text.toString().isEmpty() || binding.etContent.text.toString().isBlank()) {
                Toast.makeText(cxt, getString(R.string.enter_reflect), Toast.LENGTH_SHORT).show()
            } else {
                viewModel.insertReflect(
                    id = binding.spReflect.selectedItemId,
                    content = binding.etContent.text.toString()
                )
            }
        }

        viewModel.isReflectState.observe(viewLifecycleOwner) {
            if (it is ViewState.Load) LoadingDialogFragment.show(parentFragmentManager)
            if (it !is ViewState.Initial && it !is ViewState.Load) LoadingDialogFragment.dismiss()
            when (it) {
                is ViewState.Data -> {
                    val reflect = it.data
                    Toast.makeText(cxt, reflect.msg, Toast.LENGTH_SHORT).show()
                    if (reflect.isSuccess) findNavController().navigateUp()
                }
            }
        }
    }
}