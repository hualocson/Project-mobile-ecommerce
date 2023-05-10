package com.app.e_commerce_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.app.e_commerce_app.R
import com.app.e_commerce_app.databinding.FragmentFillProfileBinding
import com.app.e_commerce_app.model.RegisterRequest
import com.app.e_commerce_app.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FillProfileFragment : Fragment(R.layout.fragment_cart) {
    private var _binding: FragmentFillProfileBinding? = null
    private val binding get() = _binding!!
    private val userViewModel: UserViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFillProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val controller = findNavController()
        binding.btnFill.setOnClickListener()
        {
            //Check dữ liệu nhập vào có rỗng hay không
            if (binding.fillFirstname.text.toString()
                    .isEmpty() || binding.fillLastname.text.toString()
                    .isEmpty() || binding.fillPhone.text.toString().isEmpty()
            ) {
                Toast.makeText(requireContext(), "Please enter all information", Toast.LENGTH_LONG)
                    .show()
            } else {
                register()
            }
        }
    }

    private fun register() {

        val controller = findNavController()
        val bundle = arguments
        val email = bundle!!.getString("email").toString()
        val password = bundle!!.getString("password").toString()
        val resgisterRequest = RegisterRequest(
            email,
            password,
            "",
            binding.fillFirstname.text.toString(),
            binding.fillLastname.text.toString(),
            binding.fillPhone.text.toString()
        )
//        userViewModel.register(resgisterRequest).observe(viewLifecycleOwner) {
//            it?.let { resource ->
//                when (resource.status) {
//                    Status.SUCCESS -> {
//                        //Thông báo đăng ký thành công
//                        Toast.makeText(requireContext(), "Register success", Toast.LENGTH_LONG)
//                            .show()
//                        //Chuyển sang màn hình login
//                        controller.navigate(R.id.loginFragment)
//                    }
//                    Status.ERROR -> {
//                        Toast.makeText(requireContext(), resource.message, Toast.LENGTH_LONG)
//                            .show()
//                    }
//                    Status.LOADING -> {
//                    }
//                }
//            }
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}