package edu.yujie.navigationex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
<<<<<<< HEAD
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.DialogFragment
import edu.yujie.navigationex.databinding.FragMyDialogBinding

/**
 * @author YuJie on 2020/11/14
 * @describe 說明
 * @param 參數
 */
=======
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import edu.yujie.navigationex.databinding.FragmentDialogMyBinding

>>>>>>> 5353c08b8648ab256f1ef3dde1fdfbf3127f072e
class MyDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
<<<<<<< HEAD
        val binding = FragMyDialogBinding.inflate(inflater, container, false)

        val dispatcher = requireActivity().onBackPressedDispatcher
        val callback = dispatcher.addCallback(viewLifecycleOwner) {
            if (isEnabled)
                Toast.makeText(requireContext(), "Can not be Back", Toast.LENGTH_SHORT).show()
            else
                requireActivity().finish()
        }

        binding.btnBack.setOnClickListener {
            callback.isEnabled = false
            callback.handleOnBackPressed()
        }

=======
        val binding = FragmentDialogMyBinding.inflate(inflater, container, false)
        isCancelable = false
        binding.button.setOnClickListener {
            findNavController().navigate(MyDialogFragmentDirections.actionFragmentDialogMyToFragmentThird())
        }
>>>>>>> 5353c08b8648ab256f1ef3dde1fdfbf3127f072e
        return binding.root
    }
}