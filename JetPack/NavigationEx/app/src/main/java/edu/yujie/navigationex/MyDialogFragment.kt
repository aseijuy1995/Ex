package edu.yujie.navigationex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import edu.yujie.navigationex.databinding.FragmentDialogMyBinding

class MyDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDialogMyBinding.inflate(inflater, container, false)
        isCancelable = false
        binding.button.setOnClickListener {
            findNavController().navigate(MyDialogFragmentDirections.actionFragmentDialogMyToFragmentThird())
        }
        return binding.root
    }
}