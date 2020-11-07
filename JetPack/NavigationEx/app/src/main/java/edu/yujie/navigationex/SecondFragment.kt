package edu.yujie.navigationex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import edu.yujie.navigationex.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSecondBinding.inflate(inflater, container, false)
        binding.tvText.setOnClickListener {
//            findNavController().navigate(SecondFragmentDirections.actionFragmentSecondToMyDialogFragment())
//            if (findNavController().popBackStack())
//                requireActivity().finish()
//            else
//                Toast.makeText(requireContext(), "popBackStack:false", Toast.LENGTH_SHORT).show()

//                findNavController().navigateUp()
        }

        binding.tvArg.text = arguments?.getString("arg")
        return binding.root
    }


}