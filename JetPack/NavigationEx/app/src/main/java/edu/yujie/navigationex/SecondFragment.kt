package edu.yujie.navigationex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
<<<<<<< HEAD
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import edu.yujie.navigationex.databinding.FragSecondBinding

/**
 * @author YuJie on 2020/11/13
 * @describe 說明
 * @param 參數
 */
class SecondFragment : Fragment() {
    private val viewModel by activityViewModels<UserViewModel>()
    private val args by navArgs<SecondFragmentArgs>()
=======
import androidx.fragment.app.Fragment
import edu.yujie.navigationex.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {
>>>>>>> 5353c08b8648ab256f1ef3dde1fdfbf3127f072e

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
<<<<<<< HEAD
        val binding = FragSecondBinding.inflate(inflater, container, false)
        binding.textView.setOnClickListener {
            val bundle = bundleOf("args" to 3)
//            findNavController().navigate(SecondFragmentDirections.actionFragmentSecondToNavGraph2())
            findNavController().navigate(R.id.action_frag_second_to_nav_graph2, bundle)
        }

        viewModel.user.observe(viewLifecycleOwner) {
            if (it == null) {
                findNavController().navigate(R.id.frag_login)
            } else {
                Toast.makeText(requireContext(), "Welcome to SecondFragment", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        val currentBackStackEntry = findNavController().currentBackStackEntry
        val savedStateHandle = currentBackStackEntry?.savedStateHandle
        savedStateHandle?.getLiveData<Boolean>(LoginFragment.LOGIN_SUCCESS)
            ?.observe(currentBackStackEntry) {
                if (!it) {
                    val startDestination = findNavController().graph.startDestination
                    val navOptions = NavOptions.Builder()
                        .setPopUpTo(startDestination, true)
                        .build()
                    findNavController().navigate(startDestination, null, navOptions)
                }
            }

        binding.tvLoginFailed.setOnClickListener {
            //to frag_first
//            savedStateHandle?.set(LoginFragment.LOGIN_SUCCESS, false)
            //to frag_login
            viewModel.user.postValue(null)
        }

        //argument
        println("${javaClass.simpleName}:args = ${args.args}")


        return binding.root
    }
=======
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


>>>>>>> 5353c08b8648ab256f1ef3dde1fdfbf3127f072e
}