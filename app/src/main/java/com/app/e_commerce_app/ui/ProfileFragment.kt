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
import com.app.e_commerce_app.common.AppSharePreference
import com.app.e_commerce_app.data.repository.TokenRepository
import com.app.e_commerce_app.databinding.FragmentProfileBinding
import com.app.e_commerce_app.utils.Status
import com.app.e_commerce_app.viewmodel.UserViewModel
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!


    private val userViewModel: UserViewModel by viewModels {
        UserViewModel.UserViewModelFactory(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val controller = findNavController()

        loadProfile()

        binding.profileLogoutLabel.setOnClickListener {
            val sharePreference = AppSharePreference(requireContext())
            val tokenRepository = TokenRepository(sharePreference)
            tokenRepository.removeToken()
            controller.navigate(R.id.loginFragment)
        }
    }

    private fun loadProfile() {
        if(userViewModel.userLiveData.value == null) {
            userViewModel.loadUserProfile().observe(viewLifecycleOwner) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            resource.data?.let { user ->
                                Picasso.get().load(user.avatar).into(binding.profileImage)
                                binding.tvUserName.text = user.name
                                binding.tvPhone.text = user.phone
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
        else {
            val user = userViewModel.userLiveData.value!!
            Picasso.get().load(user.avatar).into(binding.profileImage)
            binding.tvUserName.text = user.name
            binding.tvPhone.text = user.phone
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}