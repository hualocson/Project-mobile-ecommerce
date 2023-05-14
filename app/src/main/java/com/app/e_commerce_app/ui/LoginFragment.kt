package com.app.e_commerce_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.app.e_commerce_app.R
import com.app.e_commerce_app.base.BaseFragment
import com.app.e_commerce_app.databinding.FragmentLoginBinding
import com.app.e_commerce_app.model.LoginRequest
import com.app.e_commerce_app.viewmodel.ValidateViewModel
import com.app.e_commerce_app.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(true) {

    private val userViewModel: UserViewModel by viewModels()
    private val validateViewModel : ValidateViewModel by viewModels()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.loginViewModel = validateViewModel
        registerAllExceptionEvent(userViewModel, viewLifecycleOwner)
        registerObserverLoadingEvent(userViewModel, viewLifecycleOwner)
        registerObserverNavigateEvent(userViewModel, viewLifecycleOwner)

        binding.tvSignup.setOnClickListener {
            navigateToPage(R.id.action_loginFragment_to_signupFragment)
        }

        binding.btnLogin.setOnClickListener {
            //Nếu email hoặc password rỗng thì thông báo
            if (binding.etUsername.text.toString().isEmpty() || binding.etPassword.text.toString()
                    .isEmpty()
            ) {
                showErrorMessage("Invalid Email or Password!!")
            } else {
                Login()
            }
        }

        binding.btnGoogle.setOnClickListener()
        {
            navigateToPage(R.id.action_loginFragment_to_welcomeFragment)
        }
    }

    private fun Login() {

        val loginRequest =
            LoginRequest(binding.etUsername.text.toString(), binding.etPassword.text.toString())

        val isChecked = binding.ckbLogin.isChecked


        userViewModel.login(loginRequest)
        userViewModel.setRemember(isChecked)
    }
}