package com.app.e_commerce_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.app.e_commerce_app.R
import com.app.e_commerce_app.databinding.FragmentLoginBinding
import com.app.e_commerce_app.viewmodel.UserViewModel


class LoginFragment : Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val userViewModel: UserViewModel by lazy {
        ViewModelProvider(
            this,
            UserViewModel.UserViewModelFactory(requireActivity().application)
        )[UserViewModel::class.java]
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
//            val loginRequest =
//                LoginRequest(binding.etUsername.text.toString(), binding.etPassword.text.toString())

//            userViewModel.login(loginRequest).observe(viewLifecycleOwner) {
//                it?.let { resource ->
//                    when (resource.status) {
//                        Status.SUCCESS -> {
//                            resource.data?.let { response ->
//                                Toast.makeText(requireContext(), response.data?.accountId.toString(), Toast.LENGTH_LONG).show()
//                            }
//                        }
//                        Status.ERROR -> {
//                            Toast.makeText(requireContext(), resource.message, Toast.LENGTH_LONG).show()
//                        }
//                        Status.LOADING -> {
//
//                        }
//                    }
//                }
//            }
        }
    }
}