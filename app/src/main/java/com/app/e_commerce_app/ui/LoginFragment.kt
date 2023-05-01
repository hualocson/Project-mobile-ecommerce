package com.app.e_commerce_app.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.app.e_commerce_app.MyApplication
import com.app.e_commerce_app.R
import com.app.e_commerce_app.common.AppSharePreference
import com.app.e_commerce_app.data.repository.TokenRepository
import com.app.e_commerce_app.data.repository.UserRepository
import com.app.e_commerce_app.databinding.FragmentLoginBinding
import com.app.e_commerce_app.model.LoginRequest
import com.app.e_commerce_app.utils.Status
import com.app.e_commerce_app.viewmodel.UserViewModel


class LoginFragment : Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!


    private val userViewModel: UserViewModel by viewModels {
        UserViewModel.UserViewModelFactory(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val controller = findNavController()
        binding.tvSignup.setOnClickListener {
            controller.navigate(R.id.signupFragment)
        }

        binding.btnLogin.setOnClickListener {
            val loginRequest =
                LoginRequest(binding.etUsername.text.toString(), binding.etPassword.text.toString())

            val isChecked = binding.ckbLogin.isChecked

            userViewModel.login(loginRequest).observe(viewLifecycleOwner) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            resource.data?.let { tokenModel ->
                                userViewModel.setRemember(isChecked)
                                controller.navigate(R.id.homeFragment)
                            }
                        }
                        Status.ERROR -> {
                            Toast.makeText(requireContext(), resource.message, Toast.LENGTH_LONG)
                                .show()
                        }
                        Status.LOADING -> {

                        }
                    }
                }
            }
        }
    }
}