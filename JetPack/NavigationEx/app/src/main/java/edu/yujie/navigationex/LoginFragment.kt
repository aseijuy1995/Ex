package edu.yujie.navigationex

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.fragment.findNavController
import edu.yujie.navigationex.databinding.FragLoginBinding

/**
 * @author YuJie on 2020/11/13
 * @describe 說明
 * @param 參數
 */
class LoginFragment : Fragment() {
    private lateinit var binding: FragLoginBinding
    private val viewModel by activityViewModels<UserViewModel>()
    private lateinit var savedStateHandle: SavedStateHandle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        savedStateHandle = findNavController().previousBackStackEntry!!.savedStateHandle
        savedStateHandle.set(LOGIN_SUCCESS, false)

        binding.btnLogin.setOnClickListener {
            login()
        }


    }

    private fun login() {
        if (TextUtils.isEmpty(
                binding.etAccount.text.toString().trim()
            ) || TextUtils.isEmpty(binding.etPassword.text.toString().trim())
        ) {
            Toast.makeText(requireContext(), "Empty", Toast.LENGTH_SHORT).show()
        } else {
            viewModel.login(
                binding.etAccount.text.toString().trim(),
                binding.etPassword.text.toString().trim()
            ).observe(viewLifecycleOwner) {
                if (it == LoginResult.Success) {
                    viewModel.user.postValue(
                        UserBean(
                            0,
                            "name",
                            binding.etAccount.text.toString().trim(),
                            binding.etPassword.text.toString().trim(),
                            "description"
                        )
                    )
                    savedStateHandle.set(LOGIN_SUCCESS, true)
                    findNavController().popBackStack()
                } else {
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    companion object {
        val LOGIN_SUCCESS = "LOGIN_SUCCESS"
    }
}