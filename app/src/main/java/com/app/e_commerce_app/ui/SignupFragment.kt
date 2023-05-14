package com.app.e_commerce_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.app.e_commerce_app.R
import com.app.e_commerce_app.base.BaseFragment
import com.app.e_commerce_app.databinding.FragmentAddressBinding
import com.app.e_commerce_app.databinding.FragmentSignupBinding
import com.app.e_commerce_app.model.PreSignupRequest
import com.app.e_commerce_app.utils.Status
import com.app.e_commerce_app.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.github.muddz.styleabletoast.StyleableToast

@AndroidEntryPoint
class SignupFragment : BaseFragment<FragmentSignupBinding>(true) {
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSignupBinding {
        return FragmentSignupBinding.inflate(inflater, container, false)
    }

    private val userViewModel: UserViewModel by viewModels()
    private fun listenClickEvent() {
        binding.signupHeader.btnLeft.setOnClickListener {
            navigateBack()
        }
        binding.tvLogin.setOnClickListener {
            navigateToPage(R.id.action_signupFragment_to_loginFragment)
        }
    }

    private fun observerEvent() {
        registerAllExceptionEvent(userViewModel, viewLifecycleOwner)
        registerObserverLoadingEvent(userViewModel, viewLifecycleOwner)
        registerObserverNavigateEvent(userViewModel, viewLifecycleOwner)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listenClickEvent()
        observerEvent()
        binding.lifecycleOwner = viewLifecycleOwner
        binding.userViewModel = userViewModel
        binding.btnSignup.setOnClickListener {
            //Check null for all fields
            if (binding.etUsername.text.toString().isEmpty() || binding.etPassword.text.toString()
                    .isEmpty() || binding.etPasswordcf.text.toString().isEmpty()
            ) {
                StyleableToast.makeText(requireContext(), "Please enter all information", R.style.ErrorToast).show()
            }
            else {
                //Check password and confirm password
                if (binding.etPassword.text.toString() != binding.etPasswordcf.text.toString()) {
                    StyleableToast.makeText(requireContext(), "Password not match", R.style.ErrorToast).show()
                } else {
                    checkEmail()
                }
            }
        }
    }



    private fun checkEmail()
    {
        val preSignupRequest = PreSignupRequest(
            binding.etUsername.text.toString(),
            binding.etPassword.text.toString()
        )

        userViewModel.checkEmail(preSignupRequest)
        userViewModel.checkSuccess.observe(viewLifecycleOwner) {
            if (it == true) {
                val action: NavDirections = SignupFragmentDirections.actionSignupFragmentToFillProfileFragment(
                    preSignupRequest
                )
                navigateAction(action)
            }
        }
    }
}