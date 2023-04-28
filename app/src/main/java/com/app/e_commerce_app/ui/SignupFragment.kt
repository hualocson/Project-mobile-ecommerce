package com.app.e_commerce_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.app.e_commerce_app.R
import com.app.e_commerce_app.databinding.FragmentHomepageBinding
import com.app.e_commerce_app.databinding.FragmentLoginBinding
import com.app.e_commerce_app.databinding.FragmentSignupBinding
import com.app.e_commerce_app.model.LoginRequest
import com.app.e_commerce_app.utils.Status
import com.app.e_commerce_app.viewmodel.UserViewModel
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel

class SignupFragment : Fragment(R.layout.fragment_signup) {

    private var _binding : FragmentSignupBinding? = null
    private val binding get() = _binding!!

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
    }
}