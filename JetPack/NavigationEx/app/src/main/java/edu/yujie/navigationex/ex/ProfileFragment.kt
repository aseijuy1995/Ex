package edu.yujie.navigationex.ex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import edu.yujie.navigationex.R
import edu.yujie.navigationex.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private val viewModel by activityViewModels<UserViewModel>()
    private val TAG = javaClass.simpleName

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProfileBinding.inflate(inflater, container, false)

        viewModel.user.observe(viewLifecycleOwner) {
            if (it != null)
                showWelcomeMsg()
            else
                findNavController().navigate(R.id.fragment_login)
        }

        val currentBackStackEntry = findNavController().currentBackStackEntry
        val savedStateHandle = currentBackStackEntry?.savedStateHandle

        savedStateHandle?.getLiveData<Boolean>(LoginFragment.LOGIN_SUCCESS)
            ?.observe(currentBackStackEntry) {
                println("$TAG:LoginResult:$it")
                if (!it) {
                    val startDestination = findNavController().graph.startDestination
                    val navOptions = NavOptions.Builder()
                        .setPopUpTo(startDestination, true)
                        .build()
                    findNavController().navigate(startDestination, null, navOptions)
                }
            }

        return binding.root
    }

    private fun showWelcomeMsg() {
        Toast.makeText(requireContext(), "showWelcomeMsg", Toast.LENGTH_SHORT).show()
    }
}