package com.app.e_commerce_app.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import com.app.e_commerce_app.R
import com.app.e_commerce_app.base.BaseFragment
import com.app.e_commerce_app.databinding.FragmentProfileBinding
import com.app.e_commerce_app.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(false) {

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(inflater, container, false)
    }

    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userViewModel.fetchUser()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.userViewModel = userViewModel

        registerAllExceptionEvent(userViewModel, viewLifecycleOwner)
        registerObserverLoadingEvent(userViewModel, viewLifecycleOwner)
        registerObserverNavigateEvent(userViewModel, viewLifecycleOwner)

        binding.btnLogout.setOnClickListener {
            userViewModel.logout()
            navigateToPage(R.id.action_profileFragment_to_loginFragment)
        }

        binding.btnAddress.setOnClickListener {
            navigateToPage(R.id.action_profileFragment_to_addressFragment)
        }
        binding.btnEditProfile.setOnClickListener {
            val action: NavDirections = ProfileFragmentDirections.actionProfileFragmentToFillProfileFragment(
                isLogged = true
            )
            navigateAction(action)
        }
    }

    private fun loadProfile() {
//        if(userViewModel.userLiveData.value == null) {
//            userViewModel.loadUserProfile().observe(viewLifecycleOwner) {
//                it?.let { resource ->
//                    when (resource.status) {
//                        Status.SUCCESS -> {
//                            resource.data?.let { user ->
//                                Picasso.get().load(user.avatar).into(binding.profileImage)
//                                binding.tvUserName.text = user.name
//                                binding.tvPhone.text = user.phone
//                            }
//                        }
//                        Status.ERROR -> {
//                            Toast.makeText(requireContext(), resource.message, Toast.LENGTH_LONG)
//                                .show()
//                        }
//                        Status.LOADING -> {
//                            Toast.makeText(requireContext(), "LOADING in profile", Toast.LENGTH_SHORT)
//                                .show()
//                        }
//                    }
//                }
//            }
//        }
//        else {
//            val user = userViewModel.userLiveData.value!!
//            Picasso.get().load(user.avatar).into(binding.profileImage)
//            binding.tvUserName.text = user.name
//            binding.tvPhone.text = user.phone
//        }
    }
}