package edu.yujie.navigationex.ex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import edu.yujie.navigationex.R
import edu.yujie.navigationex.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private val viewModel by activityViewModels<UserViewModel>()
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var binding: FragmentLoginBinding

    companion object {
        const val LOGIN_SUCCESS = "LOGIN_SUCCESS"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.btnLogin.setOnClickListener {
            binding.pbLoading.visibility = View.VISIBLE
            login(binding.etAccount.text.toString(), binding.etPassword.text.toString())
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        savedStateHandle = findNavController().previousBackStackEntry?.savedStateHandle!!
        savedStateHandle.set(LOGIN_SUCCESS, false)
    }

    private fun login(account: String, password: String) {
        viewModel.login(account, password).observe(viewLifecycleOwner) {
            when (it) {
                LoginResult.Empty -> Toast.makeText(
                    requireContext(),
                    "account or password is Empty",
                    Toast.LENGTH_SHORT
                ).show()
                LoginResult.Success -> {
                    savedStateHandle.set(LOGIN_SUCCESS, true)
                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
                LoginResult.Failed -> Toast.makeText(
                    requireContext(),
                    "account or password is wrong",
                    Toast.LENGTH_SHORT
                ).show()
            }
            binding.pbLoading.visibility = View.GONE
        }
    }
}
