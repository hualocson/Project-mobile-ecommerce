package com.app.e_commerce_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.app.e_commerce_app.R
import com.app.e_commerce_app.databinding.FragmentSignupBinding
import com.app.e_commerce_app.model.PreSignupRequest
import com.app.e_commerce_app.utils.Status
import com.app.e_commerce_app.viewmodel.UserViewModel

class SignupFragment : Fragment(R.layout.fragment_signup) {

    private var _binding : FragmentSignupBinding? = null
    private val binding get() = _binding!!

    private val userViewModel: UserViewModel by viewModels {
        UserViewModel.UserViewModelFactory(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val controller = findNavController()
        binding.tvLogin.setOnClickListener {
            controller.navigate(R.id.loginFragment)
        }

        binding.btnSignup.setOnClickListener {
            //Check password and confirm password
            if (binding.etPassword.text.toString() != binding.etPasswordcf.text.toString()) {
                Toast.makeText(requireContext(), "Password not match", Toast.LENGTH_LONG).show()
            }
            else
            {
                checkEmail()
            }
        }
    }

    fun checkEmail()
    {
        val controller = findNavController()
        val preSignupRequest = PreSignupRequest(
            binding.etUsername.text.toString()
        )
        userViewModel.checkEmail(preSignupRequest).observe(viewLifecycleOwner) {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        val bundle = bundleOf(
                            "email" to binding.etUsername.text.toString(),
                            "password" to binding.etPassword.text.toString()
                        )
                        controller.navigate(R.id.fillProfileFragment, bundle)
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